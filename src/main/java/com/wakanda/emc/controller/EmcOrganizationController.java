package com.wakanda.emc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wakanda.emc.database.EmcOrganizationRepository;
import com.wakanda.emc.database.EmcUserRepository;
import com.wakanda.emc.model.EmcOrganization;
import com.wakanda.emc.model.EmcUser;
import com.wakanda.emc.dto.CreateOrgRequest;
import com.wakanda.emc.dto.ApproveApplicantRequest;

@RestController
@RequestMapping("/api/organizations")
public class EmcOrganizationController {
    EmcOrganizationRepository orgRepo;
    EmcUserRepository userRepo;

    public EmcOrganizationController(EmcOrganizationRepository orgRepo, EmcUserRepository userRepo) {
        this.orgRepo = orgRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrganization(@RequestBody CreateOrgRequest createOrgRequest) {
        if (createOrgRequest.getOrgHandle() == null || createOrgRequest.getOrgHandle().equals("") ||
            createOrgRequest.getOrgName() == null || createOrgRequest.getOrgName().equals("") ||
            createOrgRequest.getDescription() == null || createOrgRequest.getDescription().equals("") ||
            createOrgRequest.getCreator() == null || createOrgRequest.getCreator().equals("")
            ) {
                return new ResponseEntity<>("Invalid Org handle, name, description or creator", HttpStatus.BAD_REQUEST);
        }
        if (orgRepo.findByOrgHandle(createOrgRequest.getOrgHandle()) == null) {
            EmcOrganization org = new EmcOrganization();
            org.setOrgHandle(createOrgRequest.getOrgHandle());
            org.setOrgName(createOrgRequest.getOrgName());
            org.setCreator(createOrgRequest.getCreator());
            org.setDescription(createOrgRequest.getDescription());
            org.setAppliedVolunteers(List.of());
            org.setApprovedVolunteers(List.of());
            org.setAdministrators(List.of());
            orgRepo.save(org);
            return new ResponseEntity<>(org.getOrgHandle(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid Org handle, name, description or creator", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getall")
    public EmcOrganization[] getOrganizations() {
        List<EmcOrganization> orgList = orgRepo.findAll();
        EmcOrganization[] result = orgList.toArray(new EmcOrganization[0]);
        return result;
    }

    @GetMapping("/getOrganizationsForUser")
    public EmcOrganization[] getOrganizationsForUser(@RequestParam("user") String userHandle,
                                                      @RequestParam("isAdmin") boolean isAdmin) {
        List<EmcOrganization> orgList = isAdmin ? orgRepo.findByCreatorOrAdministrators(userHandle, userHandle)
                                                : orgRepo.findByApprovedVolunteers(userHandle);
        EmcOrganization[] result = orgList.toArray(new EmcOrganization[0]);
        return result;
    }

    @PostMapping("/applyToVolunteer")
    public ResponseEntity<String> applyToVolunteer(@RequestParam("applicant") String applicantHandle,
                                                           @RequestParam("organization") String orgHandle) {
        EmcUser user = userRepo.findByUserHandle(applicantHandle);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (user.getOrgHandles() != null && user.getOrgHandles().contains(orgHandle)) {
            return new ResponseEntity<>("User is already a member of the organization", HttpStatus.BAD_REQUEST);
        }

        EmcOrganization organization = orgRepo.findByOrgHandle(orgHandle);
        if (organization == null) {
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }
        if (organization.getAppliedVolunteers() != null && organization.getAppliedVolunteers().contains(applicantHandle)) {
            return new ResponseEntity<>("User has already applied to join the organization", HttpStatus.BAD_REQUEST);
        }

        if (organization.getAdministrators() != null && organization.getAdministrators().contains(applicantHandle)) {
            return new ResponseEntity<>("User is already an administrator of the organization", HttpStatus.BAD_REQUEST);
        }

        organization.getAppliedVolunteers().add(applicantHandle);
        orgRepo.save(organization);
        return new ResponseEntity<>("Successfully applied to join organization", HttpStatus.OK);
    }

    @PostMapping("/approveApplicant")
    public ResponseEntity<String> approveApplicant(@RequestBody ApproveApplicantRequest approveApplicantRequest) {
        String approverHandle = approveApplicantRequest.getApproverHandle();

        EmcOrganization organization = orgRepo.findByOrgHandle(approveApplicantRequest.getOrgHandle());
        if (organization == null) {
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }
        if (!organization.getCreator().equals(approverHandle) && !organization.getAdministrators().contains(approverHandle)) {
            return new ResponseEntity<>("User is not the creator or an administraror of the organization", HttpStatus.UNAUTHORIZED);
        }
        for (int j=0;j<approveApplicantRequest.getApprovedUsers().length; j++) {
            String applicantHandle = approveApplicantRequest.getApprovedUsers()[j];
            EmcUser applicant = userRepo.findByUserHandle(applicantHandle);

            if (applicant == null) {
                return new ResponseEntity<>("Applicant not found", HttpStatus.NOT_FOUND);
            }
            if (!organization.getAppliedVolunteers().contains(applicantHandle)) {
                return new ResponseEntity<>("Applicant is not pending approval", HttpStatus.BAD_REQUEST);
            }
            organization.getAppliedVolunteers().remove(applicantHandle);
            organization.getApprovedVolunteers().add(applicantHandle);
            applicant.getOrgHandles().add(approveApplicantRequest.getOrgHandle());
            userRepo.save(applicant);
            orgRepo.save(organization);
        }
    
        return new ResponseEntity<>("Applicant approved", HttpStatus.OK);
    }
}

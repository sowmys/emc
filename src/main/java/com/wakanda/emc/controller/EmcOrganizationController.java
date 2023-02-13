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
import com.wakanda.emc.dto.CreateOrgResponse;

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
    public ResponseEntity<CreateOrgResponse> createOrganization(@RequestBody CreateOrgRequest createOrgRequest) {
        if (createOrgRequest.getOrgHandle() == null || createOrgRequest.getOrgHandle().equals("") ||
            createOrgRequest.getOrgName() == null || createOrgRequest.getOrgName().equals("") ||
            createOrgRequest.getDescription() == null || createOrgRequest.getDescription().equals("") ||
            createOrgRequest.getCreator() == null || createOrgRequest.getCreator().equals("")
            ) {
            return ResponseEntity.ok(new CreateOrgResponse(false, null));
        }
        EmcOrganization orgCreated = createOrg(createOrgRequest);
        if (orgCreated != null) 
            return ResponseEntity.ok((new CreateOrgResponse(true, orgCreated.getOrgHandle())));
        else
            return ResponseEntity.ok(new CreateOrgResponse(false, null));
    }

    @GetMapping("/getall")
    public EmcOrganization[] getOrganizations() {
        List<EmcOrganization> orgList = orgRepo.findAll();
        EmcOrganization[] result = orgList.toArray(new EmcOrganization[0]);
        return result;
    }

    @PostMapping("/applyToJoinOrganization")
    public ResponseEntity<String> applyToJoinOrganization(@RequestParam("applicant") String applicantHandle,
                                                           @RequestParam("organization") String orgHandle) {
        EmcUser user = userRepo.findByUserHandle(applicantHandle);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (user.getOrgHandles().contains(orgHandle)) {
            return new ResponseEntity<>("User is already a member of the organization", HttpStatus.BAD_REQUEST);
        }

        EmcOrganization organization = orgRepo.findByOrgHandle(orgHandle);
        if (organization == null) {
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }
        if (organization.getAppliedUsers().contains(applicantHandle)) {
            return new ResponseEntity<>("User has already applied to join the organization", HttpStatus.BAD_REQUEST);
        }

        organization.getAppliedUsers().add(applicantHandle);
        orgRepo.save(organization);
        return new ResponseEntity<>("Successfully applied to join organization", HttpStatus.OK);
    }

    @PostMapping("/approveApplicant")
    public ResponseEntity<String> approveApplicant(@RequestParam("approver") String approverHandle,
                                                   @RequestParam("organization") String orgHandle,
                                                   @RequestParam("applicant") String applicantHandle) {
        EmcUser applicant = userRepo.findByUserHandle(applicantHandle);
        EmcOrganization organization = orgRepo.findByOrgHandle(orgHandle);
        if (applicant == null || organization == null) {
            return new ResponseEntity<>("Creator, applicant, or organization not found", HttpStatus.NOT_FOUND);
        }
        if (!organization.getCreator().equals(approverHandle)) {
            return new ResponseEntity<>("User is not the creator of the organization", HttpStatus.UNAUTHORIZED);
        }
        if (!organization.getAppliedUsers().contains(applicantHandle)) {
            return new ResponseEntity<>("Applicant is not pending approval", HttpStatus.BAD_REQUEST);
        }
        organization.getAppliedUsers().remove(applicantHandle);
        orgRepo.save(organization);
        applicant.getOrgHandles().add(orgHandle);
        userRepo.save(applicant);
        return new ResponseEntity<>("Applicant approved", HttpStatus.OK);
    }

    private EmcOrganization createOrg(CreateOrgRequest createOrgRequest) {
        if (orgRepo.findByOrgHandle(createOrgRequest.getOrgHandle()) != null) {
            return null;
        }
        EmcOrganization org = new EmcOrganization();
        org.setOrgHandle(createOrgRequest.getOrgHandle());
        org.setOrgName(createOrgRequest.getOrgName());
        org.setCreator(createOrgRequest.getCreator());
        org.setDescription(createOrgRequest.getDescription());
        org.setAppliedUsers(List.of());
        orgRepo.save(org);
        return org;
    }
}

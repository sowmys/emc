package com.wakanda.emc.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wakanda.emc.database.EmcOrganizationRepository;
import com.wakanda.emc.model.EmcOrganization;

@RestController
public class EmcOrganizationController {
    EmcOrganizationRepository orgRepo;

    public EmcOrganizationController(EmcOrganizationRepository orgRepo) {
        this.orgRepo = orgRepo;
    }

    @PostMapping("/organizations")
    public EmcOrganization createOrganization(@RequestBody EmcOrganization org) {
        orgRepo.save(org);
        return org;
    }

    @GetMapping("/organizations")
    public EmcOrganization[] getOrganizations() {
        List<EmcOrganization> orgList = orgRepo.findAll();
        EmcOrganization[] result = orgList.toArray(new EmcOrganization[0]);
        return result;
    }

    @GetMapping("/organizations/{name}")
    public EmcOrganization getOrganizationByName(@PathVariable String name) {
        List<EmcOrganization> orgs = orgRepo.findByName(name);
        return orgs != null ? orgs.get(0): null;
    }
}

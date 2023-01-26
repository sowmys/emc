package com.wakanda.emc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wakanda.emc.model.EmcOrganization;

@RestController
public class EmcOrganizationController {
    long nextId = 0;
    List<EmcOrganization> orgList = new ArrayList<EmcOrganization>();

    public EmcOrganizationController() {
    }

    @PostMapping("/organizations")
    public EmcOrganization createOrganization(@RequestBody EmcOrganization org) {
        org.setId(nextId);
        nextId++;
        orgList.add(org);
        return org;
    }

    @GetMapping("/organizations")
    public EmcOrganization[] getOrganizations() {
        EmcOrganization[] result = orgList.toArray(new EmcOrganization[0]);
        return result;
    }

    @GetMapping("/organizations/{id}")
    public EmcOrganization getOrganization(@PathVariable Long id) {
        return orgList.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @PutMapping("/organizations/{id}")
    public EmcOrganization updateOrganization(@PathVariable Long id, @RequestBody EmcOrganization org) {
        EmcOrganization orgToUpdate = orgList.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        orgToUpdate.setName(org.getName());
        orgToUpdate.setDescription(org.getDescription());
        orgToUpdate.setCreator(org.getCreator());
        return org;
    }
}

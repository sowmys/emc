package com.wakanda.emc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import com.wakanda.emc.database.EmcEventRepository;
import com.wakanda.emc.database.EmcUserRepository;
import com.wakanda.emc.dto.CreateEventRequest;
import com.wakanda.emc.model.EmcEvent;

@RestController
@RequestMapping("/api/events")
public class EmcEventController {
	private EmcEventRepository eventRepo;
    public EmcEventController(EmcEventRepository eventRepo, EmcUserRepository userRepo) {
        this.eventRepo = eventRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody CreateEventRequest event) {
        if (event.getOrgHandle() == null || event.getOrgHandle().equals("") ||
            event.getName() == null || event.getName().equals("") ||
            event.getDescription() == null || event.getDescription().equals("") ||
            event.getCreator() == null || event.getCreator().equals("")) {
                return new ResponseEntity<>("Invalid Org handle, name, description or creator", HttpStatus.BAD_REQUEST);
        }

        if (eventRepo.findByOrgHandleAndName(event.getOrgHandle(), event.getName()) != null) {
            return new ResponseEntity<>("Event already exists", HttpStatus.BAD_REQUEST);
        }

        EmcEvent newEvent = new EmcEvent();
        newEvent.setOrgHandle(event.getOrgHandle());
        newEvent.setName(event.getName());
        newEvent.setDescription(event.getDescription());
        newEvent.setCreator(event.getCreator());
        newEvent.setDateTime(event.getDateTime());
        newEvent.setLocation(event.getLocation());
        newEvent.setTasks(List.of());
        eventRepo.save(newEvent);

        return ResponseEntity.ok("Event created successfully");
    }

    @GetMapping("/getEventsByApprovedVolunteer")
    public List<EmcEvent> getEventsByApprovedVolunteer(@RequestParam("user") String userHandle,
                                                      @RequestParam("org") String orgHandle) {
        List<EmcEvent> eventList = eventRepo.findByOrgHandleAndApprovedVolunteers(orgHandle, userHandle);
        
        return eventList;
    }

    @GetMapping("/getEventsByCreator")
    public List<EmcEvent> getEventsByCreator(@RequestParam("user") String userHandle,
                                                      @RequestParam("org") String orgHandle) {
        List<EmcEvent> eventList = eventRepo.findByOrgHandleAndCreator(orgHandle, userHandle);
        
        return eventList;
    }

    @GetMapping("/getEventsWithOpenVolunteerSpots")
    public List<EmcEvent> getEventsWithOpenVolunteerSpots(@RequestParam("user") String userHandle,
                                                      @RequestParam("org") String orgHandle) {
        List<EmcEvent> eventList = eventRepo.findByOrgHandle(orgHandle);
        eventList.removeIf(event -> event.getOpenVolunteerSpots() <= 0
                                  ||event.getApprovedVolunteers().contains(userHandle)
                                  ||event.getCreator().equals(userHandle)
                                  ||event.getAppliedVolunteers().contains(userHandle));
        return eventList;
    }

    @PostMapping("/applyToVolunteer")
    public ResponseEntity<String> applyToVolunteer(@RequestParam("applicant") String applicantHandle,
                                                           @RequestParam("organization") String orgHandle,
                                                           @RequestParam("event") String eventName) {
        EmcEvent event = eventRepo.findByOrgHandleAndName(orgHandle, eventName);
        if (event == null) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
        if (event.getOpenVolunteerSpots() <= 0) {
            return new ResponseEntity<>("Event is full", HttpStatus.BAD_REQUEST);
        }
        if (event.getApprovedVolunteers().contains(applicantHandle)) {
            return new ResponseEntity<>("User is already an approved volunteer", HttpStatus.BAD_REQUEST);
        }
        if (event.getAppliedVolunteers().contains(applicantHandle)) {
            return new ResponseEntity<>("User has already applied to volunteer", HttpStatus.BAD_REQUEST);
        }
        if (event.getCreator().equals(applicantHandle)) {
            return new ResponseEntity<>("User is the creator of the event", HttpStatus.BAD_REQUEST);
        }
        event.getAppliedVolunteers().add(applicantHandle);
        event.setOpenVolunteerSpots(event.getOpenVolunteerSpots() - 1);
        eventRepo.save(event);
        return ResponseEntity.ok("User applied to volunteer successfully");
    }

    @PostMapping("/approveApplicant")
    public ResponseEntity<String> approveApplicant(@RequestParam("org") String orgHandle,
                                                   @RequestParam("event") String eventName,
                                                   @RequestParam ("applicants") String[] applicantHandles) {
        EmcEvent event = eventRepo.findByOrgHandleAndName(orgHandle, eventName);
        if (event == null) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
        for (String applicantHandle : applicantHandles) {
            if (!event.getAppliedVolunteers().contains(applicantHandle)) {
                return new ResponseEntity<>("User has not applied to volunteer", HttpStatus.BAD_REQUEST);
            }
            if (event.getApprovedVolunteers().contains(applicantHandle)) {
                return new ResponseEntity<>("User is already an approved volunteer", HttpStatus.BAD_REQUEST);
            }
            if (event.getCreator().equals(applicantHandle)) {
                return new ResponseEntity<>("User is the creator of the event", HttpStatus.BAD_REQUEST);
            }

            event.getAppliedVolunteers().remove(applicantHandle);
            event.getApprovedVolunteers().add(applicantHandle);
        }

        eventRepo.save(event);
        return ResponseEntity.ok("User approved to volunteer successfully");
    }
}

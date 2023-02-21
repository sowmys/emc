package com.wakanda.emc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import com.wakanda.emc.database.EmcEventRepository;
import com.wakanda.emc.database.EmcTaskRepository;
import com.wakanda.emc.dto.CreateEventRequest;
import com.wakanda.emc.model.EmcEvent;

@RestController
@RequestMapping("/api/events")
public class EmcEventController {
	private EmcEventRepository eventRepo;
    private EmcTaskRepository taskRepo;

    public EmcEventController(EmcEventRepository eventRepo, EmcTaskRepository taskRepo) {
        this.eventRepo = eventRepo;
        this.taskRepo = taskRepo;
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
        newEvent.setDate(event.getDate());
        newEvent.setTime(event.getTime());
        newEvent.setLocation(event.getLocation());
        newEvent.setTasks(List.of());
        eventRepo.save(newEvent);

        return ResponseEntity.ok("Event created successfully");
    }


}

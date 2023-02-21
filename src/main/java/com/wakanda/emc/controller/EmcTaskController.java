package com.wakanda.emc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.wakanda.emc.database.EmcEventRepository;
import com.wakanda.emc.database.EmcTaskRepository;
import com.wakanda.emc.dto.CreateTaskRequest;
import com.wakanda.emc.model.EmcTask;

@RestController
@RequestMapping("/api/tasks")
public class EmcTaskController {
    EmcEventRepository eventRepo;
    EmcTaskRepository taskRepo;

    public EmcTaskController(EmcEventRepository eventRepo, EmcTaskRepository taskRepo) {
        this.eventRepo = eventRepo;
        this.taskRepo = taskRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest task) {
        if (task.getOrgHandle() == null || task.getOrgHandle().equals("") ||
            task.getName() == null || task.getName().equals("") ||
            task.getDescription() == null || task.getDescription().equals("") ||
            task.getEventName() == null || task.getEventName().equals("")) {
                return new ResponseEntity<>("Invalid Org handle, name, description or event name", HttpStatus.BAD_REQUEST);
        }

        if (taskRepo.findByOrgHandleAndEventNameAndName(task.getOrgHandle(), task.getEventName(), task.getName()) != null) {
            return new ResponseEntity<>("Task already exists", HttpStatus.BAD_REQUEST);
        }

        EmcTask newTask = new EmcTask();
        newTask.setOrgHandle(task.getOrgHandle());
        newTask.setEventName(task.getEventName());
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setCompleted(task.isCompleted());
        newTask.setAssignee(task.getAssignee());
        newTask.setHours(task.getHours());
        newTask.setNotes(task.getNotes());
        taskRepo.save(newTask);

        return ResponseEntity.ok("Task created successfully");
    }
}

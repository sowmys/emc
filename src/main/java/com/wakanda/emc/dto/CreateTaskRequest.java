package com.wakanda.emc.dto;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private String name;
    private String description;
    private String eventName;
    private String orgHandle;
    private String assignee;
    private boolean completed;
    private int hours;
    private String notes;
}

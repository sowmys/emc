package com.wakanda.emc.dto;

import lombok.Data;

@Data
public class CreateEventRequest {
    private String name;
    private String description;
    private String date;
    private String time;
    private String location;
    private String orgHandle;
    private String creator;
}

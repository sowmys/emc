package com.wakanda.emc.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CreateEventRequest {
    private String name;
    private String description;
    private Date dateTime;
    private String location;
    private String orgHandle;
    private String creator;
}

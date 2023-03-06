package com.wakanda.emc.model;

import java.util.List;
import java.util.ArrayList;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import java.util.Date;

import lombok.Data;

@Document(collection = "emc-events")
@Data
public class EmcEvent {
    @Indexed(name = "uniqueEventField", unique = true)
    private String name;
    @Indexed(name = "uniqueEventField", unique = true)
    private String orgHandle;
    private String description;
    private String location;
    private Date dateTime;
    private String creator;
    private int openVolunteerSpots;
    private List<String> appliedVolunteers = new ArrayList<>();
    private List<String> approvedVolunteers = new ArrayList<>();
    private List<String> tasks = new ArrayList<>();
    
    @Id
    private ObjectId id;
}

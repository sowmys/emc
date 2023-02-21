package com.wakanda.emc.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;

import lombok.Data;

@Document(collection = "emc-tasks")
@Data
public class EmcTask {
    @Indexed(name = "uniqueTaskField", unique = true)
    private String name;
    private String description;
    @Indexed(name = "uniqueTaskField", unique = true)
    private String eventName;
    @Indexed(name = "uniqueTaskField", unique = true)
    private String orgHandle;
    private String assignee;
    private int hours;
    private boolean completed;
    private String notes;
        
    @Id
    private ObjectId id;
}

package com.wakanda.emc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "organizations")
@Data
public class EmcOrganization {
    private String name;
    private String creator;
    private String description;
    @Id
    private ObjectId id;

    public EmcOrganization(String name, String description, String creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
    }
}

package com.wakanda.emc.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "organizations")
@Data
public class EmcOrganization {
    @Indexed(unique = true)
    private String orgHandle;
    private String orgName;
    private String creator;
    private String description;
    private List<String> appliedUsers;
    
    @Id
    private ObjectId id;
}




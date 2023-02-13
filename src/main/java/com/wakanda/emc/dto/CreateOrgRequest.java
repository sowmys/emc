package com.wakanda.emc.dto;

import lombok.Data;

@Data
public class CreateOrgRequest {
    private String orgHandle;
    private String orgName;
    private String creator;
    private String description;    
}

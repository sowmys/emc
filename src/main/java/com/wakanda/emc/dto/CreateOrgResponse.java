package com.wakanda.emc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrgResponse {
    private String errorMessage;
    private String orgHandle;
}

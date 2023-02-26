package com.wakanda.emc.dto;

import lombok.Data;

@Data
public class ApproveApplicantRequest {
    private String approverHandle;
    private String orgHandle;
    private String[] approvedUsers;
}

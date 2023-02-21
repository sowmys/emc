package com.wakanda.emc.dto;

import lombok.Data;

@Data
public class ApproveApplicantRequest {
    private String approverHandle;
    private OrgInfo[] orgs;

    @Data
    public static class OrgInfo {
        private String orgHandle;
        private String[] approvedUsers;
    }
}

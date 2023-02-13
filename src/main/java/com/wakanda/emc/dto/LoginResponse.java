package com.wakanda.emc.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String userHandle;
    private List<String> orgHandles;
}

package com.wakanda.emc.viewmodel;

import lombok.Data;

@Data
public class RegisterRequest {
        private String userHandle;
        private String firstName;
        private String lastName;
        private String address;
        private String email;
        private String phone;
        private String password;
}

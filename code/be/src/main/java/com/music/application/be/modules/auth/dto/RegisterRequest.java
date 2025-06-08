package com.music.application.be.modules.auth.dto;

import com.music.application.be.modules.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String email;

    private String phone;

    private String password;

    private Role role;
}


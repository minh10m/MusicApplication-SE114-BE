package com.music.application.be.modules.user.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String phone;
    private String password;
}
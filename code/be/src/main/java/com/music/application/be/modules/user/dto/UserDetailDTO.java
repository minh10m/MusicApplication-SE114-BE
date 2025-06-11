package com.music.application.be.modules.user.dto;

import com.music.application.be.modules.role.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private Long id;
    private Role role;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
package com.music.application.be.modules.user;

import com.music.application.be.modules.user.dto.UserDetailDTO;
import com.music.application.be.modules.user.dto.UserResponseDTO;
import com.music.application.be.modules.user.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDTO> getUserById(@PathVariable Long userId) {
        UserDetailDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailDTO> updateUser(
            @PathVariable Long userId,
            @RequestPart("user") UserUpdateDTO userUpdateDTO,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile) throws IOException {

        UserDetailDTO updatedUser = userService.updateUser(userId, userUpdateDTO, avatarFile);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
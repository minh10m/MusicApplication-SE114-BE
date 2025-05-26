package com.music.application.be.modules.user;

import com.music.application.be.modules.cloudinary.CloudinaryService;
import com.music.application.be.modules.user.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    public User updateUser(Long userId, UserDTO userDTO, MultipartFile avatarFile) throws IOException {
        User user = getUserById(userId);

        if (userDTO.getUsername() != null && !userDTO.getUsername().trim().isEmpty()) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPhone() != null && !userDTO.getPhone().trim().isEmpty()) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().trim().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarUrl = cloudinaryService.uploadFile(avatarFile, "image");
            user.setAvatar(avatarUrl);
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}
package com.music.application.be.modules.user;


import com.music.application.be.modules.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "users", key = "#userId")
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    @CachePut(value = "users", key = "#userId")
    public User updateUser(Long userId, UserDTO userDTO) {
        User user = getUserById(userId);
        user.setUsername(userDTO.getUsername());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    @Cacheable(value = "allUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @CacheEvict(value = {"users", "allUsers"}, key = "#userId")
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}
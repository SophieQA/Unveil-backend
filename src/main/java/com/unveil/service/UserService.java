package com.unveil.service;

import com.unveil.dto.UserDto;
import com.unveil.model.User;
import com.unveil.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user (mock - no security checks)
     */
    @Transactional
    public UserDto register(String username, String password) {
        log.info("Registering user: {}", username);

        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }

        // Create new user (mock - no password hashing)
        User user = User.builder()
            .username(username)
            .password(password)  // Mock: plain password (NOT SECURE)
            .build();

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", username);
        return toUserDto(savedUser);
    }

    /**
     * Login user (mock - no security checks)
     */
    public UserDto login(String username, String password) {
        log.info("Login attempt for user: {}", username);

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        User user = optionalUser.get();

        // Mock: plain password comparison (NOT SECURE)
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password for user: " + username);
        }

        log.info("User logged in successfully: {}", username);
        return toUserDto(user);
    }

    /**
     * Get user by ID
     */
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::toUserDto);
    }

    /**
     * Get user by username
     */
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::toUserDto);
    }

    /**
     * Check if user exists
     */
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private UserDto toUserDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .build();
    }
}

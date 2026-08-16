package com.popam.learning_spring.project_web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getUser_id());
        dto.setUsername(user.getUsername());
        return dto;
    }

    private User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        return user;
    }

    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        return toResponseDTO(user);
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = toEntity(dto);
        User savedUser = userRepository.save(user);
        return toResponseDTO(savedUser);
    }

    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentials());
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentials();
        }
        return toResponseDTO(user);
    }
}

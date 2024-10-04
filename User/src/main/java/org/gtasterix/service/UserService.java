package org.gtasterix.service;

import org.gtasterix.dto.UserDTO;
import org.gtasterix.exception.UserNotFoundException;
import org.gtasterix.mapper.UserMapper;
import org.gtasterix.model.User;
import org.gtasterix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Fetch all users
    public List<UserDTO> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching all users: " + e.getMessage(), e);
        }
    }

    // Fetch user by ID
    public UserDTO getUserById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id));
            return userMapper.toDTO(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user by ID: " + e.getMessage(), e);
        }
    }

    // Create new user
    public UserDTO createUser(UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            User savedUser = userRepository.save(user);
            return userMapper.toDTO(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating user: " + e.getMessage(), e);
        }
    }

    // Update existing user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id));
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDTO(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating user: " + e.getMessage(), e);
        }
    }

    // Delete user
    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id));
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting user: " + e.getMessage(), e);
        }
    }
}

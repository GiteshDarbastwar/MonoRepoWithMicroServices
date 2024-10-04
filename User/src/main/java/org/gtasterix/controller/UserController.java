package org.gtasterix.controller;

import org.gtasterix.dto.UserDTO;
import org.gtasterix.service.UserService;
import org.gtasterix.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Response> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return new ResponseEntity<>(new Response("Success", users, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch users", e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return new ResponseEntity<>(new Response("Success", user, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("User not found", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            return new ResponseEntity<>(new Response("User created successfully", createdUser, false), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to create user", e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return new ResponseEntity<>(new Response("User updated successfully", updatedUser, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to update user", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(new Response("User deleted successfully", null, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to delete user", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }
}

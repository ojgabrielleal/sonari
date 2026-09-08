package com.sonari.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.dto.UserRequestDTO;
import com.sonari.dto.UserResponseDTO;
import com.sonari.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Handles user management and profile information.")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(
        summary = "List all users",
        description = "Returns a list of all registered users."
    )
    @SecurityRequirement(name = "bearerAuth")
    public List<UserResponseDTO> index(){
        return userService.index();
    }

    @GetMapping("/{user_uuid}")
    @Operation(
        summary = "Get user by uuid",
        description = "Returns a user based on the provided UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public UserResponseDTO show(@PathVariable("uuid") UUID uuid){
        return userService.show(uuid);
    }

    @PostMapping
    @Operation(
        summary = "Create user",
        description = "Creates a new user with the provided data."
    )
    @SecurityRequirement(name = "bearerAuth")
    public UserResponseDTO store(@RequestBody UserRequestDTO user){
        return userService.store(user);
    }

    @PutMapping("/{user_uuid}")
    @Operation(
        summary = "Update user",
        description = "Updates an existing user based on the provided UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public UserResponseDTO update(@PathVariable("user_uuid") UUID uuid, @RequestBody UserRequestDTO user){
        return userService.update(uuid, user);
    }

    @DeleteMapping("/{user_uuid}")
    @Operation(
        summary = "Delete user",
        description = "Deletes an existing user based on the provided UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public void delete(@PathVariable("user_uuid") UUID uuid){
        userService.delete(uuid);
    }
}

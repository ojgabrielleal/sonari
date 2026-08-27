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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDTO> index(){
        return userService.index();
    }

    @GetMapping("/{uuid}")
    public UserResponseDTO show(@PathVariable UUID uuid){
        return userService.show(uuid);
    }

    @PostMapping
    public UserResponseDTO store(@RequestBody UserRequestDTO user){
        return userService.store(user);
    }

    @PutMapping("/{uuid}")
    public UserResponseDTO update(@PathVariable UUID uuid, @RequestBody UserRequestDTO user){
        return userService.update(uuid, user);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        userService.delete(uuid);
    }
}

package com.sonari.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sonari.dto.UserRequestDTO;
import com.sonari.dto.UserResponseDTO;
import com.sonari.entity.User;
import com.sonari.mapper.UserMapper;
import com.sonari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> index(){
        return userRepository.findAll()
            .stream()
            .map(userMapper::toResponse)
            .toList();
    }

    public UserResponseDTO show(UUID uuid){
        return userMapper.toResponse(
            userRepository.findByUuid(uuid).orElseThrow()
        );
    }

    public UserResponseDTO store(UserRequestDTO data){
        User user = userRepository.save(
            userMapper.toEntity(data)
        );

        return userMapper.toResponse(user);
    }

    public UserResponseDTO update(UUID uuid, UserRequestDTO data){
        User user = userRepository.findByUuid(uuid).orElseThrow();

        userMapper.updateEntity(user, data);

        return userMapper.toResponse(
            userRepository.save(user)
        );
    }

    public void delete(UUID uuid){
        User user = userRepository.findByUuid(uuid).orElseThrow();
        userRepository.delete(user);
    }
}

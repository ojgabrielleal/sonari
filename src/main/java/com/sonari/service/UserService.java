package com.sonari.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sonari.dto.UserResponseDTO;
import com.sonari.entity.User;
import com.sonari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> index(){
        return userRepository.findAll()
            .stream()
            .map(UserResponseDTO::from)
            .toList();
    }

    public UserResponseDTO show(UUID uuid){
        return UserResponseDTO.from(
            userRepository.findByUuid(uuid).orElseThrow()
        );
    }

    public User store(User data){
        return userRepository.save(data);
    }

    public User update(UUID uuid, User data){
        User user = userRepository.findByUuid(uuid).orElseThrow();

        user.setFullName(data.getFullName());
        user.setNickName(data.getNickName());
        user.setCity(data.getCity());
        user.setState(data.getState());
        user.setCountry(data.getCountry());

        return userRepository.save(user);
    }

    public void delete(UUID uuid){
        User user = userRepository.findByUuid(uuid).orElseThrow();
        userRepository.delete(user);
    }
}

package com.sonari.mapper;

import org.springframework.stereotype.Component;

import com.sonari.dto.UserRequestDTO;
import com.sonari.dto.UserResponseDTO;
import com.sonari.entity.User;

@Component
public class UserMapper {
    
    public UserResponseDTO toResponse(User user){
        return new UserResponseDTO(
            user.getUuid(),
            user.getFullName(),
            user.getNickName()
        );
    }

    public User toEntity(UserRequestDTO data){
        User user = new User();

        user.setFullName(data.fullName());
        user.setNickName(data.nickName());

        return user;
    }

    public void updateEntity(User user, UserRequestDTO data){
        user.setFullName(data.fullName());
        user.setNickName(data.nickName());
    }

}

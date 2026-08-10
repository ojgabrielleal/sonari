package com.sonari.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.sonari.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO{

    private UUID uuid;
    private String fullName;
    private String nickName;
    private String city;
    private String state;
    private String country;
    private LocalDate birthDate;

    public static UserResponseDTO from (User user){
        return new UserResponseDTO(
            user.getUuid(),
            user.getFullName(),
            user.getNickName(),
            user.getCity(),
            user.getState(),
            user.getCountry(),
            user.getBirthDate()
        );
    }

}

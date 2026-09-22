package com.sonari.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sonari.dto.UserRequestDTO;
import com.sonari.dto.UserResponseDTO;
import com.sonari.entity.User;

import net.datafaker.Faker;

public class UserFactory {

    private final static Faker faker = new Faker();

    public static User create(){
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setUsername(faker.credentials().username());
        user.setPassword(faker.credentials().password());
        user.setFullName(faker.name().fullName());
        user.setNickName(faker.credentials().username());

        return user;
    }

    public static List<User> create(int quantity){
        List<User> users = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            users.add(create());
        }

        return users;
    }

    
    public static UserResponseDTO createWithResponse(){
        return new UserResponseDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.credentials().username()
        );
    }

    public static UserResponseDTO createWithResponse(User user){
        return new UserResponseDTO(
            user.getUuid(),
            user.getFullName(),
            user.getNickName()
        );
    }

    public static List<UserResponseDTO> createWithResponse(int quantity){
        List<UserResponseDTO> users = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            users.add(createWithResponse());
        }

        return users;
    }

    public static UserRequestDTO createWithRequest(){
        return new UserRequestDTO(
            faker.name().fullName(),
            faker.credentials().username()
        );
    }
}

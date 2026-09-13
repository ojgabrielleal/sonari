package com.sonari.factories;

import java.util.ArrayList;
import java.util.List;

import com.sonari.entity.User;

import net.datafaker.Faker;

public class UserFactory {

    private final static Faker faker = new Faker();

    public static User create(){
        User user = new User();
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
}

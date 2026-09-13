package com.sonari.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonari.dto.UserRequestDTO;
import com.sonari.dto.UserResponseDTO;
import com.sonari.entity.User;
import com.sonari.factories.UserFactory;
import com.sonari.mapper.UserMapper;
import com.sonari.repository.UserRepository;
import com.sonari.service.UserService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock 
    private UserRepository userRepository;
    @Mock 
    private UserMapper userMapper;
    @InjectMocks 
    private UserService userService;

    private final static Faker faker = new Faker();

    @Test 
    void shouldReturnsAllUsers(){
        List<User> users = UserFactory.create(5);

        when(userRepository.findAll())
            .thenReturn(users);

        for(User user: users){
            UserResponseDTO userResponse =  new UserResponseDTO(
                user.getUuid(),
                user.getFullName(),
                user.getNickName()
            );

            when(userMapper.toResponse(user))
                .thenReturn(userResponse);
        }

        List<UserResponseDTO> result = userService.index();
        assertEquals(5, result.size());
    }

    @Test 
    void shouldReturnUniqueUser(){
        User user = UserFactory.create();

        UserResponseDTO userResponse =  new UserResponseDTO(
            user.getUuid(),
            user.getFullName(),
            user.getNickName()
        );

        when(userRepository.findByUuid(user.getUuid()))
            .thenReturn(Optional.of(user));

        when(userMapper.toResponse(user))
            .thenReturn(userResponse);

        UserResponseDTO result = userService.show(user.getUuid());
        
        assertEquals(user.getUuid(), result.uuid());
        assertEquals(user.getFullName(), result.fullName());
        assertEquals(user.getNickName(), result.nickName());
    }

    @Test 
    void shouldStoreUser(){
        User user = UserFactory.create();

        UserRequestDTO userRequest = new UserRequestDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.credentials().username()
        );

        UserResponseDTO userResponse =  new UserResponseDTO(
            user.getUuid(),
            user.getFullName(),
            user.getNickName()
        );


        when(userRepository.save(user))
            .thenReturn(user);

        when(userMapper.toEntity(userRequest))
            .thenReturn(user);

        when(userMapper.toResponse(user))
            .thenReturn(userResponse);

        UserResponseDTO result = userService.store(userRequest); 

        assertEquals(user.getUuid(), result.uuid());
        assertEquals(user.getFullName(), result.fullName());
        assertEquals(user.getNickName(), result.nickName());
    }

    @Test
    void shouldDeleteUser(){
        User user = UserFactory.create();

        when(userRepository.findByUuid(user.getUuid()))
            .thenReturn(Optional.of(user));

        userService.delete(user.getUuid());

        verify(userRepository).delete(user);
    }
}

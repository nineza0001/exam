package com.bangkok.bank.exam.service;

import com.bangkok.bank.exam.dto.request.UserRequestDto;
import com.bangkok.bank.exam.dto.request.UserResponseDto;
import com.bangkok.bank.exam.entity.UserEntity;
import com.bangkok.bank.exam.exception.UserNotFoundException;
import com.bangkok.bank.exam.mapper.UserMapper;
import com.bangkok.bank.exam.repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserService userService;



    @BeforeEach
    void setup() {
        userMapper = new UserMapper();
        userService = new UserService(
                userRepository,
                userMapper
        );
    }


    @Test
    void shouldGetUserByIdSuccessfully() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("John");
        user.setUsername("john");
        user.setEmail("john@test.com");
        user.setPhone("123456");

        when(userRepository.findById(eq(1L)))
                .thenReturn(Optional.of(user));

        val userResponse = userService.getUser(1L);

        assertEquals("john@test.com", userResponse.email());
        assertEquals("123456", userResponse.phone());


        verify(userRepository)
                .findById(1L);
    }

    @Test
    void shouldNotFoundUserById() {
        when(userRepository.findById(eq(1L)))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUser(1L)
        );

        verify(userRepository)
                .findById(1L);
    }


    @Test
    void shouldSaveUserSuccessfully() {
        val userId = 1L;
        val userRequestDto = new UserRequestDto("John",
                "john",
                "john@test.com",
                "123456",
                "example.com");



        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("John");
        user.setUsername("john");
        user.setEmail("john@test.com");
        user.setPhone("123456");

        when(userRepository.findById(eq(1L)))
                .thenReturn(Optional.of(user));

        when(userRepository.save(eq(user)))
                .thenReturn(user);

        val saveResponse = userService.updateUser(userId, userRequestDto);

        assertEquals("john@test.com", saveResponse.email());
        assertEquals("123456", saveResponse.phone());

        verify(userRepository)
                .findById(userId);

        verify(userRepository)
                .save(any());

    }


    @Test
    void shouldSaveUserFailureWhenNotFoundUser() {
        val userId = 1L;
        val userRequestDto = new UserRequestDto("John",
                "john",
                "john@test.com",
                "123456",
                "example.com");


        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());


        assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(userId, userRequestDto)
        );

        verify(userRepository)
                .findById(userId);

        verify(userRepository, never())
                .save(any());

    }
}
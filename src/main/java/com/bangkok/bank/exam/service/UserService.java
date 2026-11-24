package com.bangkok.bank.exam.service;

import com.bangkok.bank.exam.dto.request.UserRequestDto;
import com.bangkok.bank.exam.dto.request.UserResponseDto;
import com.bangkok.bank.exam.entity.UserEntity;
import com.bangkok.bank.exam.exception.UserNotFoundException;
import com.bangkok.bank.exam.mapper.UserMapper;
import com.bangkok.bank.exam.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDto getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDto createUser(UserRequestDto user) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public UserResponseDto updateUser(long id, UserRequestDto userRequest) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userMapper.updateEntity(userEntity, userRequest);

        return userMapper.toDto(userRepository.save(userEntity));
    }

    public void deleteUser(Long id) {
        log.debug("Delete user with id: {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }

}

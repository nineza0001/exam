package com.bangkok.bank.exam.mapper;

import com.bangkok.bank.exam.dto.request.UserRequestDto;
import com.bangkok.bank.exam.dto.request.UserResponseDto;
import com.bangkok.bank.exam.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toDto(UserEntity user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getWebsite()
        );
    }

    public UserEntity toEntity(UserRequestDto request) {
        UserEntity user = new UserEntity();

        user.setName(request.name());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setWebsite(request.website());

        return user;
    }

    public void updateEntity(UserEntity user, UserRequestDto request) {
        user.setName(request.name());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setWebsite(request.website());
    }

}
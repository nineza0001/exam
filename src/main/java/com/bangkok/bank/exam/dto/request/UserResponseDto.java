package com.bangkok.bank.exam.dto.request;


public record UserResponseDto(
        Long id,
        String name,
        String username,
        String email,
        String phone,
        String website
) {}

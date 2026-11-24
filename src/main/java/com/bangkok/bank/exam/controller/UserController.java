package com.bangkok.bank.exam.controller;

import com.bangkok.bank.exam.dto.request.UserRequestDto;
import com.bangkok.bank.exam.dto.request.UserResponseDto;
import com.bangkok.bank.exam.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> postUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto user = userService.createUser(request);
        URI location = URI.create("/users/" + user.id());
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> putUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequestDto request) {

        UserResponseDto updateUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

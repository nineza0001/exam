package com.bangkok.bank.exam;

import com.bangkok.bank.exam.entity.UserEntity;
import com.bangkok.bank.exam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


    @Override
    public void run(String... args) throws Exception {

        InputStream inputStream =
                getClass().getResourceAsStream("/static/users.json");

        List<UserEntity> users =
                objectMapper.readValue(
                        inputStream,
                        new TypeReference<>() {
                        }
                );

        userRepository.saveAll(users);
    }
}
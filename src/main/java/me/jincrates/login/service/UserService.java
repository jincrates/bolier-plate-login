package me.jincrates.login.service;

import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.User;
import me.jincrates.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User create(UserDTO dto) {

        User user = dtoToEntity(dto);

        if (user == null || user.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        String email = user.getEmail();

        if (repository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return repository.save(user);
    }

    public User getByCredentials(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    private User dtoToEntity(UserDTO dto) {
        User user = User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return user;
    }
}

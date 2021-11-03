package me.jincrates.login.service;

import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.UserEntity;
import me.jincrates.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserEntity create(UserDTO dto) {

        UserEntity user = dtoToEntity(dto);

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

    public UserEntity getByCredentials(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    private UserEntity dtoToEntity(UserDTO dto) {
        UserEntity user = UserEntity.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return user;
    }
}

package me.jincrates.login.service;

import javassist.bytecode.DuplicateMemberException;
import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.Authority;
import me.jincrates.login.entity.User;
import me.jincrates.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import me.jincrates.login.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional // 데이터 쓰기: readOnly = false
    public User signup(UserDTO userDTO) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDTO.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = toEntity(userDTO);

        return userRepository.save(user);
    }

    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    public User toEntity(UserDTO dto) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User entity = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return entity;
    }
}
package me.jincrates.login.controller;

import me.jincrates.login.dto.ResponseDTO;
import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.UserEntity;
import me.jincrates.login.security.TokenProvider;
import me.jincrates.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService service;
    private final TokenProvider tokenProvider;

    @PostMapping(path = "/v1/signup", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> registerUserV1(@RequestBody UserDTO userDTO) {
        try {
            //요청을 이용해 저장할 사용자 만들기
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .id(userDTO.getId())
                    .build();

            // 서비스를 이용해 리포지터리에 사용자 저장
            UserEntity registerUser = service.create(userDTO);

            UserDTO responseUserDTO = UserDTO.builder()
                    .email(registerUser.getEmail())
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping(path = "/v2/signup", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<UserDTO> registerUserV2(@RequestBody UserDTO userDTO) {

        UserEntity registerUser = service.create(userDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{email}")
                .buildAndExpand(registerUser.getEmail())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/v1/signin")
    public ResponseEntity<?> authenticateV(@RequestBody UserDTO userDTO) {

        UserEntity user = service.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword());

        if (user != null) {
            // 토큰 생성
            String token = tokenProvider.create(user);
            log.info("token : {}", token);
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/v2/signin")
    public ResponseEntity<UserDTO> authenticateV2(@RequestBody UserDTO userDTO) {

        UserEntity user = service.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword());

        if (user == null) {
            throw new RuntimeException("Login failed.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{email}")
                .buildAndExpand(user.getEmail(), user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}

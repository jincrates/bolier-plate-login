package com.jincrates.bolierplatelogin.controller;

import com.jincrates.bolierplatelogin.dto.ResponseDTO;
import com.jincrates.bolierplatelogin.dto.UserDTO;
import com.jincrates.bolierplatelogin.entity.UserEntity;
import com.jincrates.bolierplatelogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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

    @PostMapping(path = "/v1/signup", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> registerUserV1(@RequestBody UserDTO userDTO) {
        try {
            //요청을 이용해 저장할 사용자 만들기
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
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

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> authenticate(@RequestBody UserDTO userDTO) {

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

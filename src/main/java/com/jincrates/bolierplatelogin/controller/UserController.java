package com.jincrates.bolierplatelogin.controller;

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

    @PostMapping(path = "/signup", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {

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

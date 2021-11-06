package me.jincrates.login.controller;

import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.jwt.service.TokenProvider;
import me.jincrates.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private final TokenProvider tokenProvider;

    @PostMapping(path = "/v1/signup", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> registerUserV1(@RequestBody UserDTO userDTO) {
        return null;
    }

    @PostMapping("/v1/signin")
    public ResponseEntity<?> authenticateV(@RequestBody UserDTO userDTO) {
        return null;
    }
}

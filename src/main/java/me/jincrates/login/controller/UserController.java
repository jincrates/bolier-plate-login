package me.jincrates.login.controller;

import lombok.var;
import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.User;
import me.jincrates.login.jwt.service.TokenProvider;
import me.jincrates.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    @PostMapping(path = "/signup", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<User> signup(@Valid @RequestBody UserDTO userDTO) {
        User result = service.signup(userDTO);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(service.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(service.getUserWithAuthorities(username).get());
    }

}

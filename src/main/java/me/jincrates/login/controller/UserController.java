package me.jincrates.login.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.User;
import me.jincrates.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api") @Api(tags = {"사용자 API"})
public class UserController {

    private final UserService service;

    @ApiOperation(value = "회원가입", notes = "사용자의 이메일, 패스워드, 닉네임을 입력받아 DB에 저장한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "사용자 이메일"),
            @ApiImplicitParam(name = "password", value = "사용자 패스워드"),
            @ApiImplicitParam(name = "nickname", value = "사용자 닉네임")})
    @PostMapping(path = "/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.signup(userDTO));
    }

    @ApiOperation(value = "내정보", notes = "요청한 사용자의 권한을 읽어와 사용자 정보를 리턴한다.")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(service.getMyUserWithAuthorities().get());
    }

    @ApiOperation(value = "유저정보(관리자 권한)", notes = "사용자의 이메일을 입력받아 관리자에게 사용자 정보를 리턴한다.")
    @ApiImplicitParam(name = "username", value = "사용자 이메일")
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(service.getUserWithAuthorities(username).get());
    }

}

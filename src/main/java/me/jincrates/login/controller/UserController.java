package me.jincrates.login.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.jincrates.login.dto.UserDTO;
import me.jincrates.login.entity.User;
import me.jincrates.login.exception.UserNotFoundException;
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
@RequestMapping("/api/users") @Api(tags = {"사용자 API"})
public class UserController {

    private final UserService service;

    @ApiOperation(value = "회원가입", notes = "사용자의 이메일, 패스워드, 닉네임을 입력받아 DB에 저장한다.")
    @PostMapping(path = "/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.signup(userDTO));
    }

    @ApiOperation(value = "내 정보 조회", notes = "요청한 사용자의 권한을 읽어와 사용자 정보를 리턴한다.")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(service.getMyUserWithAuthorities().get());
    }

    @ApiOperation(value = "회원정보 조회(관리자 권한)", notes = "사용자의 이메일을 입력받아 관리자에게 사용자 정보를 리턴한다.")
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {

        User user = service.getUserWithAuthorities(username).get();

        if (user == null) {
            throw  new UserNotFoundException(String.format("ID[%s] not found", username));
        }

        return ResponseEntity.ok(service.getUserWithAuthorities(username).get());
    }

    @ApiOperation(value = "회원정보 수정", notes = "패스워드, 닉네임을 입력받아 사용자의 정보를 수정한다.")
    @PutMapping("/user")
    public ResponseEntity<Integer> modifyUserInfo(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.modifyUserInfo(userDTO));
    }

    @ApiOperation(value = "회원탈퇴", notes = "사용자의 이메일을 입력받아 상태값을 변경한다.")
    @PutMapping("/user/{username}")
    public ResponseEntity<Integer> deleteUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(service.deleteUser(username));
    }
}

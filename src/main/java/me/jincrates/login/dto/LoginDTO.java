package me.jincrates.login.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@ApiModel(value = "로그인 데이터 전송 객체")
public class LoginDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}

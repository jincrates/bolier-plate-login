package me.jincrates.login.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@ApiModel(value = "토큰 데이터 전송 객체")
public class TokenDTO {

    private String token;
}

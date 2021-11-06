package me.jincrates.login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@ApiModel(value = "권한 데이터 전송 객체")
public class AuthorityDTO {
    private String authorityName;
}

package me.jincrates.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //스웨거 페이지에 소개될 설명들
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Login API")
                .version("1.0.0")
                .description("로그인 기능 구현을 위한 API")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("me.jincrates.login"))
                .paths(PathSelectors.any())
                .build();
    }
}

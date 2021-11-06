package me.jincrates.login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jincrates.login.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    private Set<AuthorityDTO> authorityDtoSet;

    public static UserDTO from(User user) {
        if(user == null) return null;

        return UserDTO.builder()
                .username(user.getUsername())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}

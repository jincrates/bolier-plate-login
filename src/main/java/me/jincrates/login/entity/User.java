package me.jincrates.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "user") @ApiModel(value = "사용자 Entity")
public class User {

    @ApiModelProperty(value = "사용자 번호", required = true)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "사용자 이메일(id)", required = true)
    @Column(name = "username", length = 50, unique = true)
    private String username; //실제 사용자 키. 사용자의 이메일을 받는다.

    @JsonIgnore
    @Column(name ="password", nullable = false)
    private String password;  // 패스워드

    @ApiModelProperty(value = "사용자 닉네임", required = true)
    @Column(name = "nickname", length = 50)
    private String nickname; //사용자의 이름

    @JsonIgnore
    @Column(name = "activated", nullable = false)
    private boolean activated;// 사용자 상태

    @ApiModelProperty(value = "사용자 권한", required = true)
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}

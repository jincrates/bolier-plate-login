package me.jincrates.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId; //사용자의 이메일을 받는다.

    @Column(name ="username", nullable = false)
    private String username;  //사용자의 이름

    @JsonIgnore
    @Column(name ="password", nullable = false)
    private String password;  // 패스워드

    @JsonIgnore
    @Column(name = "activated", nullable = false)
    private boolean activated;// 사용자 상태

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}

package me.jincrates.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy = "uuid")
    private String id;  //사용자에게 고유하게 부여되는 id

    @Column(nullable = false, unique = true)
    private String email;  // 사용자의 email, 아이디와 같은 기능을 한다.

    @Column(nullable = false)
    private String username;  //사용자의 이름

    @JsonIgnore
    @Column(nullable = false)
    private String password;  // 패스워드

    @JsonIgnore
    @Column(nullable = false)
    private boolean activated; // 사용자 상태

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Authority> authority = new HashSet<>();

    public void addUserAuthority(Authority authority) {
        this.authority.add(authority);
    }
}

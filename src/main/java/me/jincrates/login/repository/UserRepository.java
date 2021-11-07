package me.jincrates.login.repository;

import me.jincrates.login.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    //해당 쿼리 실행 후 영속성 컨텍스트를 clear()
    @Modifying(clearAutomatically = true)
    @Query(value = "update user u set u.password = :password, u.nickname = :nickname where u.username = :username", nativeQuery = true)
    int modifyUserInfo(@Param("username") String username, @Param("password") String password, @Param("nickname") String nickname);

    @Modifying(clearAutomatically = true)
    @Query(value = "update user u set u.activated = false where u.username = :username", nativeQuery = true)
    int deleteUser(@Param("username") String username);
}

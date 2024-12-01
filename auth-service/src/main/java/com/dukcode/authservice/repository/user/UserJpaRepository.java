package com.dukcode.authservice.repository.user;

import com.dukcode.authservice.entity.user.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findUserEntityByUserId(String userId);

}

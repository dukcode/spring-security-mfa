package com.dukcode.authservice.repository.otp;

import com.dukcode.authservice.entity.otp.OtpEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpJpaRepository extends JpaRepository<OtpEntity, Long> {

  Optional<OtpEntity> findOtpEntityByUserId(String userId);
}

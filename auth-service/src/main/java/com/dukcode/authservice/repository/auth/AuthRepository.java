package com.dukcode.authservice.repository.auth;

import com.dukcode.authservice.domain.User;
import com.dukcode.authservice.entity.otp.OtpEntity;
import com.dukcode.authservice.entity.user.UserEntity;
import com.dukcode.authservice.exception.InvalidAuthException;
import com.dukcode.authservice.repository.otp.OtpJpaRepository;
import com.dukcode.authservice.repository.user.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionOperations;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

  private final UserJpaRepository userJpaRepository;
  private final OtpJpaRepository otpJpaRepository;

  private final TransactionOperations readTransactionOperations;
  private final TransactionOperations writeTransactionOperations;

  public User createNewUser(User user) {
    return writeTransactionOperations.execute(status -> {
      Optional<UserEntity> userOptional = userJpaRepository.findUserEntityByUserId(
          user.getUserId());

      if (userOptional.isPresent()) {
        throw new RuntimeException(String.format("User [%s] already exists.", user.getUserId()));
      }

      return userJpaRepository.save(UserEntity.fromDomain(user)).toDomain();
    });
  }

  public User getUserByUserId(String userId) {
    return readTransactionOperations.execute(status ->
        userJpaRepository.findUserEntityByUserId(userId)
            .orElseThrow(InvalidAuthException::new)
            .toDomain());
  }

  public String getOtp(String userId) {
    return readTransactionOperations.execute(
        status -> otpJpaRepository.findOtpEntityByUserId(userId)
            .orElseThrow(
                () -> new RuntimeException(String.format("User [%s] does not exists.", userId)))
            .getOtpCode());
  }

  public void upsertOtp(String userId, String newOtpCode) {
    writeTransactionOperations.executeWithoutResult(status -> {
      Optional<OtpEntity> otpEntityOptional = otpJpaRepository.findOtpEntityByUserId(userId);

      if (otpEntityOptional.isPresent()) {
        otpEntityOptional.get().renewOpt(newOtpCode);
        return;
      }

      otpJpaRepository.save(new OtpEntity(userId, newOtpCode));
    });
  }
}

package com.dukcode.authservice.repository.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import com.dukcode.authservice.domain.User;
import com.dukcode.authservice.entity.otp.OtpEntity;
import com.dukcode.authservice.entity.user.UserEntity;
import com.dukcode.authservice.repository.otp.OtpJpaRepository;
import com.dukcode.authservice.repository.user.UserJpaRepository;
import com.dukcode.authservice.util.OtpCodeUtil;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionOperations;

@ExtendWith(MockitoExtension.class)
class AuthRepositoryTest {

  @Mock
  UserJpaRepository userJpaRepository;

  @Mock
  OtpJpaRepository otpJpaRepository;

  AuthRepository sut;

  private String userId = "danny.kim";
  private String password = "12345";

  @BeforeEach
  public void setup() {
    sut = new AuthRepository(
        userJpaRepository,
        otpJpaRepository,
        TransactionOperations.withoutTransaction(),
        TransactionOperations.withoutTransaction());
  }

  @Test
  @DisplayName("동일한 사용자 ID 로 사용자를 등록할 수 없다.")
  public void test1() {
    // given
    UserEntity userEntity = new UserEntity(userId, password);
    given(userJpaRepository.findUserEntityByUserId(userId))
        .willReturn(Optional.of(userEntity));

    // when & then
    assertThatThrownBy(() -> {
      sut.createNewUser(userEntity.toDomain());
    }).isInstanceOf(RuntimeException.class);
  }

  @Test
  @DisplayName("사용자를 등록할 수 있다.")
  public void test2() {
    // given
    String userId = "danny.kim";
    String password = "1234";
    given(userJpaRepository.findUserEntityByUserId(userId))
        .willReturn(Optional.empty());

    User user = new User(userId, password);
    given(userJpaRepository.save(any()))
        .willReturn(UserEntity.fromDomain(user));

    // when
    User result = sut.createNewUser(user);

    // then
    verify(userJpaRepository, atMostOnce()).save(UserEntity.fromDomain(user));
    assertThat(result.getUserId()).isEqualTo(userId);
    assertThat(result.getPassword()).isEqualTo(password);
  }

  @Test
  @DisplayName("OTP 가 존재하면 OTP 값을 업데이트 한다.")
  public void test3() {
    // given
    String userId = "danny.kim";
    String existOtp = "000000";
    String otp = OtpCodeUtil.generateOtpCode();
    OtpEntity otpEntity = new OtpEntity(userId, existOtp);
    given(otpJpaRepository.findOtpEntityByUserId(userId))
        .willReturn(Optional.of(otpEntity));

    // when
    sut.upsertOtp(userId, otp);

    // then
    assertThat(otp).isEqualTo(otpEntity.getOtpCode());
  }

  @Test
  @DisplayName("OTP 가 존재하지 않으면 새로운 OTP 를 생성한다.")
  public void test4() {
    // given
    String userId = "danny.kim";
    String otp = OtpCodeUtil.generateOtpCode();
    given(otpJpaRepository.findOtpEntityByUserId(userId))
        .willReturn(Optional.empty());

    // when
    sut.upsertOtp(userId, otp);

    // then
    verify(otpJpaRepository, atMostOnce()).save(any());
  }
}
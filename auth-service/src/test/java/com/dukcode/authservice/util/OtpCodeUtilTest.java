package com.dukcode.authservice.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OtpCodeUtilTest {

  @Test
  @DisplayName("6자리 숫자값이 나와야 한다.")
  public void test1() {
    String otp = OtpCodeUtil.generateOtpCode();

    assertThat(otp).containsOnlyDigits();
    assertThat(otp.length()).isEqualTo(6);
  }

}
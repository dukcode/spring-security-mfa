package com.dukcode.authservice.controller.request;

import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class SimpleOtpRequestBody {

  private final String userId;

  private String otpCode;

  @ConstructorProperties({"userId", "otpCode"})
  public SimpleOtpRequestBody(String userId, String otpCode) {
    this.userId = userId;
    this.otpCode = otpCode;
  }

}

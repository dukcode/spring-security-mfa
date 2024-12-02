package com.dukcode.authservice.controller.request;

import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class SimpleOtpRequestBody {

  private final String userId;

  private String otp;

  @ConstructorProperties({"userId", "otp"})
  public SimpleOtpRequestBody(String userId, String otp) {
    this.userId = userId;
    this.otp = otp;
  }

}

package com.dukcode.authservice.controller;

import com.dukcode.authservice.controller.request.SimpleOtpRequestBody;
import com.dukcode.authservice.controller.request.SimpleUserRequestBody;
import com.dukcode.authservice.service.OtpService;
import com.dukcode.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final OtpService otpService;
  private final UserService userService;

  @PostMapping("/api/v1/users/auth")
  public String auth(@RequestBody SimpleUserRequestBody requestBody) {
    return userService.auth(requestBody.getUserId(), requestBody.getPassword());
  }

  @PostMapping("/api/v1/otp/check")
  public boolean checkOtp(@RequestBody SimpleOtpRequestBody requestBody) {
    return otpService.checkOtp(requestBody.getUserId(), requestBody.getOtpCode());
  }
}

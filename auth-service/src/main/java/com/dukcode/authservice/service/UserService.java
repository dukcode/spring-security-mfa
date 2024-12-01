package com.dukcode.authservice.service;

import com.dukcode.authservice.domain.User;
import com.dukcode.authservice.repository.auth.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final OtpService otpService;
  private final AuthRepository authRepository;
  private final EncryptService encryptService;

  public User createNewUser(String userId, String password) {
    return authRepository.createNewUser(new User(userId, password));
  }

  public String auth(String userId, String password) {
    User user = authRepository.getUserByUserId(userId);

    if (encryptService.matches(password, user.getPassword())) {
      return otpService.renewOtp(userId);
    }

    throw new RuntimeException("Invalid password");
  }
}


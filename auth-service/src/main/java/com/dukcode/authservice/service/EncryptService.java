package com.dukcode.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EncryptService {

  private final PasswordEncoder passwordEncoder;

  public String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  public boolean matches(String toCheck, String target) {
    return passwordEncoder.matches(toCheck, target);
  }
}

package com.dukcode.authservice.service;


import com.dukcode.authservice.repository.auth.AuthRepository;
import com.dukcode.authservice.util.OtpCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OtpService {

  private final AuthRepository authRepository;

  public boolean checkOtp(String userId, String sourceOtpCode) {
    String targetOtpCode = authRepository.getOtp(userId);
    return targetOtpCode.equals(sourceOtpCode);
  }

  public String renewOtp(String userId) {
    String newOtpCode = OtpCodeUtil.generateOtpCode();
    authRepository.upsertOtp(userId, newOtpCode);
    return newOtpCode;
  }
}

package com.dukcode.authservice.entity.otp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "otp")
@Entity
public class OtpEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String userId;

  @Column
  private String otpCode;

  public OtpEntity(String userId, String otpCode) {
    this.userId = userId;
    this.otpCode = otpCode;
  }

  public void renewOpt(String newOtpCode) {
    this.otpCode = newOtpCode;
  }
}

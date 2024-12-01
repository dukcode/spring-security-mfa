package com.dukcode.authservice.controller.request;

import com.dukcode.authservice.annotation.PasswordEncryption;
import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class EncryptedUserRequestBody {

  private final String userId;

  @PasswordEncryption
  private String password;

  @ConstructorProperties({"userId", "password"})
  public EncryptedUserRequestBody(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

}

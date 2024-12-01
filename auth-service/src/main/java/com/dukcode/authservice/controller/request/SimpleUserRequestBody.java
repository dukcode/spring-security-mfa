package com.dukcode.authservice.controller.request;

import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class SimpleUserRequestBody {

  private final String userId;

  private String password;

  @ConstructorProperties({"userId", "password"})
  public SimpleUserRequestBody(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

}

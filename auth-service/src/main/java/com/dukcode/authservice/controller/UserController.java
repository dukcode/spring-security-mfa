package com.dukcode.authservice.controller;

import com.dukcode.authservice.controller.request.EncryptedUserRequestBody;
import com.dukcode.authservice.domain.User;
import com.dukcode.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @PostMapping("/api/v1/users")
  public User createNewUser(@RequestBody EncryptedUserRequestBody requestBody) {
    return userService.createNewUser(requestBody.getUserId(), requestBody.getPassword());
  }
}

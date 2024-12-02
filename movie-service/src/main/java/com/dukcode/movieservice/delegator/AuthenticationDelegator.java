package com.dukcode.movieservice.delegator;

import com.dukcode.movieservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationDelegator {

  private final RestTemplate restTemplate;

  @Value("${base-url.auth-service}")
  private String authServiceBaseUrl;

  public void restAuth(String userId, String password) {
    String url = authServiceBaseUrl + "/users/auth";

    User user = User.builder()
        .userId(userId)
        .password(password)
        .build();

    restTemplate.postForObject(url, new HttpEntity<>(user), Void.class);
  }

  public boolean restOtp(String userId, String otp) {
    String url = authServiceBaseUrl + "/otp/check";

    User user = User.builder()
        .userId(userId)
        .otp(otp)
        .build();

    ResponseEntity<Boolean> response = restTemplate.postForEntity(url, new HttpEntity<>(user),
        Boolean.class);

    return Boolean.TRUE.equals(response.getBody());
  }

}

package com.dukcode.movieservice.security;

import com.dukcode.movieservice.delegator.AuthenticationDelegator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

  private final AuthenticationDelegator delegator;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String otp = String.valueOf(authentication.getCredentials());

    boolean authenticated = delegator.restOtp(username, otp);

    if (authenticated) {
      return new OtpAuthentication(username, otp);
    }

    throw new BadCredentialsException("Invalid OTP");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return OtpAuthentication.class.isAssignableFrom(authentication);
  }
}

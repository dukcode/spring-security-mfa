package com.dukcode.movieservice.config;

import com.dukcode.movieservice.security.filter.InitialAuthenticationFilter;
import com.dukcode.movieservice.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SpringSecurity {

  private final InitialAuthenticationFilter initialAuthenticationFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

    http.addFilterBefore(initialAuthenticationFilter, BasicAuthenticationFilter.class);
    http.addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

}

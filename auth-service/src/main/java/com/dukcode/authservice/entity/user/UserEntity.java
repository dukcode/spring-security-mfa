package com.dukcode.authservice.entity.user;

import com.dukcode.authservice.domain.User;
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
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String userId;

  @Column
  private String password;

  public UserEntity(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

  public static UserEntity fromDomain(User user) {
    return new UserEntity(user.getUserId(), user.getPassword());
  }

  public User toDomain() {
    return new User(userId, password);
  }
}

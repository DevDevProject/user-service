package com.example.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    private Long id;

    private String userId;

    private String email;
    private String provider;

    private Boolean mailAuthentication;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User create(String userId, String email, String provider) {
        User user = new User();
        user.userId = userId;
        user.email = email;
        user.provider = provider;
        user.mailAuthentication = false;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        return user;
    }

    public void updateLoginTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}

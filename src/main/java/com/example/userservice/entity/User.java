package com.example.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
}

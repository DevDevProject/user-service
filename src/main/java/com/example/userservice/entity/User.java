package com.example.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String provider;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

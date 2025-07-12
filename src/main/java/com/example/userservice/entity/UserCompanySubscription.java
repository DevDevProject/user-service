package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserCompanySubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long companyId;
}

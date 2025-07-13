package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserRecruitSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long recruitId;

    public static UserRecruitSubscription create(User user, Long recruitId) {
        UserRecruitSubscription subscription = new UserRecruitSubscription();
        subscription.user = user;
        subscription.recruitId = recruitId;

        return subscription;
    }
}

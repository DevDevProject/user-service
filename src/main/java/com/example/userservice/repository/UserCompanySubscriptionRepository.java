package com.example.userservice.repository;

import com.example.userservice.entity.UserCompanySubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCompanySubscriptionRepository extends JpaRepository<UserCompanySubscription, Long> {
}

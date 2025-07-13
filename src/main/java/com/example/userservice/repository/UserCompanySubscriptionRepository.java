package com.example.userservice.repository;

import com.example.userservice.entity.UserCompanySubscription;
import com.example.userservice.entity.UserRecruitSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCompanySubscriptionRepository extends JpaRepository<UserCompanySubscription, Long> {
    List<UserRecruitSubscription> findByCompanyId(Long companyId);
}

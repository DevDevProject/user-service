package com.example.userservice.repository;

import com.example.userservice.entity.User;
import com.example.userservice.entity.UserRecruitSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRecruitSubscriptionRepository extends JpaRepository<UserRecruitSubscription, Long> {

    List<UserRecruitSubscription> findByRecruitId(Long recruitId);

    boolean existsByUserAndRecruitId(User user, Long recruitId);

    Optional<UserRecruitSubscription> findByUserAndRecruitId(User user, Long recruitId);
}

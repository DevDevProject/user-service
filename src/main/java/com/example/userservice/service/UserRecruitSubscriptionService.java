package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.entity.UserRecruitSubscription;
import com.example.userservice.repository.UserRecruitSubscriptionRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRecruitSubscriptionService {

    private final JwtUtil jwtUtil;
    private final UserRecruitSubscriptionRepository recruitSubscriptionRepository;
    private final UserRepository userRepository;

    public void addSubscription(Long recruitId, String token) {
        String userId = jwtUtil.extractUserId(token);

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty())
            throw new IllegalArgumentException("User not found " + userId);

        boolean exists = recruitSubscriptionRepository.existsByUserAndRecruitId(user.get(), recruitId);
        if (!exists) {
            UserRecruitSubscription subscription = UserRecruitSubscription.create(user.get(), recruitId);
            recruitSubscriptionRepository.save(subscription);
        }
    }

    public void removeSubscription(Long recruitId, String token) {
        String userId = jwtUtil.extractUserId(token);

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty())
            throw new IllegalArgumentException("User not found " + userId);

        Optional<UserRecruitSubscription> subscription =
                recruitSubscriptionRepository.findByUserAndRecruitId(user.get(), recruitId);

        if (subscription.isPresent())
            recruitSubscriptionRepository.delete(subscription.get());

    }
}

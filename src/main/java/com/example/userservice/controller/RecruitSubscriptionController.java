package com.example.userservice.controller;

import com.example.userservice.service.UserRecruitSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class RecruitSubscriptionController {

    private final UserRecruitSubscriptionService userRecruitSubscriptionService;

    @PostMapping("/subscription/{recruitId}")
    public ResponseEntity<?> subscribeRecruit(@PathVariable Long recruitId,
                                              @RequestHeader("Authorization") String token) {
        userRecruitSubscriptionService.addSubscription(recruitId, token);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subscription/{recruitId}")
    public ResponseEntity<?> unSubscribeRecruit(@PathVariable Long recruitId,
                                                @RequestHeader("Authorization") String token) {
        userRecruitSubscriptionService.removeSubscription(recruitId, token);

        return ResponseEntity.ok().build();
    }
}

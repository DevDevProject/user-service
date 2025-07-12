package com.example.userservice.kafka;

import com.example.userservice.entity.UserRecruitSubscription;
import com.example.userservice.kafka.dto.RecruitCreatedMessage;
import com.example.userservice.repository.UserRecruitSubscriptionRepository;
import com.example.userservice.smtp.EmailSender;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerPartitionAssignor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecruitCreatedConsumer {

    private final UserRecruitSubscriptionRepository subscriptionRepository;
    private final EmailSender emailSender;

    @KafkaListener(topics = "company.recruit.created", groupId = "user-service")
    public void consume(RecruitCreatedMessage message) {
        Long companyId = message.getCompanyId();

        List<UserRecruitSubscription> subscribers = subscriptionRepository.findByCompanyId(companyId);

        for (UserRecruitSubscription s : subscribers) {
            String email = s.getUser().getEmail();
            emailSender.sendOneEmail(
                    email,
                    message.getTitle(),
                    message.getCompanyName(),
                    message.getJobId()
            );
        }
    }
}

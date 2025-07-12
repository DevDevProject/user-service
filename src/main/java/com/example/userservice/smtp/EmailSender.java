package com.example.userservice.smtp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class EmailSender {

    @Value("${spring.mail.username}")
    private String mailAddress;

    private final JavaMailSender mailSender;

    @Async
    public void sendOneEmail(String to, String title, String companyName, Long jobId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // 수신자, 제목, 본문 등 설정
            String subject = "[AllDevHub] 신규 채용 등록 안내";
            String body = String.format("""
                <div style="font-family: Arial, sans-serif; padding: 20px;">
                    <h2 style="color: #5db6f5;">[AllDevHub] 신규 채용 공고가 등록되었습니다!</h2>
                    
                    <p><b>구독 중인 회사</b>에서 새로운 채용 공고가 등록되었습니다. 지금 확인해보세요!</p>
                    <div style="margin: 20px 0; padding: 15px; border: 1px solid #eee; border-radius: 8px; background-color: #f9f9f9;">
                        <p><strong>회사명:</strong> %s</p>
                        <p><strong>제목:</strong> %s</p>
                        <p><strong>기술 스택:</strong> %s</p>
                    </div>
                    <a href="https://alldevhub.com/recruit/%s" 
                       style="display: inline-block; padding: 10px 20px; background-color: #5db6f5; color: white; text-decoration: none; border-radius: 5px;">
                        공고 바로 보러가기
                    </a>
                    <p style="margin-top: 40px; font-size: 12px; color: #999;">
                        본 메일은 자동 발송되었으며, 회신되지 않습니다.
                    </p>
                </div>
                """, companyName, title, jobId);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setFrom(mailAddress, "AllDevHub");

            mailSender.send(mimeMessage);

        } catch (MessagingException e){
            System.out.println("Error sending email" + e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

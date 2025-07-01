package com.example.userservice.service;

import com.example.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(userRequest);

        // 사용자 이메일 추출 (provider마다 다름)
        String email = extractEmail(userRequest.getClientRegistration().getRegistrationId(), oauthUser.getAttributes());

        // 여기서 DB에 사용자 등록 or 조회 등 작업 가능 (생략 가능)

        // JWT 생성
        String jwt = jwtUtil.generateToken(email);

        // OAuth2User에 JWT 포함 (추가 정보로 넘기기)
        Map<String, Object> attributes = new HashMap<>(oauthUser.getAttributes());
        attributes.put("jwt", jwt);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email");
    }

    private String extractEmail(String provider, Map<String, Object> attributes) {
        if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email");
        } else if ("github".equals(provider)) {
            return (String) attributes.get("email");
        } else { // google 등
            return (String) attributes.get("email");
        }
    }
}


package com.example.userservice.service;

import com.example.userservice.oauth2.common.GithubOAuth2User;
import com.example.userservice.oauth2.common.GoogleOAuth2User;
import com.example.userservice.oauth2.common.KakaoOAuth2User;
import com.example.userservice.oauth2.common.OAuth2UserCommon;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserCommon userCommon;
        if ("google".equals(provider)) {
            userCommon = new GoogleOAuth2User(oauthUser.getAttributes());
        } else if("github".equals(provider)) {
            userCommon = new GithubOAuth2User(oauthUser.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }

        String email = Optional.ofNullable(userCommon.getEmail())
                .filter(e -> !e.isBlank())
                .orElse(userCommon.getName());

        String providerId = userCommon.getProviderId();

        Map<String, Object> attributes = new HashMap<>(oauthUser.getAttributes());
        attributes.put("email", email);
        attributes.put("provider", provider);
        attributes.put("providerId", providerId);
        attributes.put("name", email);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "name");
    }
}


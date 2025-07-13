package com.example.userservice.oauth2;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${front.redirect.url}")
    private String redirectUrl;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String provider = oauthUser.getAttribute("provider");
        String providerId = oauthUser.getAttribute("providerId");

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            User newUser = User.create(providerId, email, provider);
            userRepository.save(newUser);
        }

        String token = jwtUtil.generateToken(user.get().getUserId(), email);

        String url = redirectUrl + "?token=" + token;
        response.sendRedirect(url);
    }
}


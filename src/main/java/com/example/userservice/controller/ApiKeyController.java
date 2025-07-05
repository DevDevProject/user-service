package com.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class ApiKeyController {

    private final RSAPublicKey publicKey;

    @GetMapping("/oauth2/public-key")
    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}

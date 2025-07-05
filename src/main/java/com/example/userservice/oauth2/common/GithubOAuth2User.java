package com.example.userservice.oauth2.common;

import java.util.Map;

public class GithubOAuth2User implements OAuth2UserCommon {

    private Map<String, Object> attributes;

    public GithubOAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("login");
    }
}

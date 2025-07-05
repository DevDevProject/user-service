package com.example.userservice.oauth2.common;

import java.util.Map;

public class GoogleOAuth2User implements OAuth2UserCommon {

    private Map<String, Object> attributes;

    public GoogleOAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}

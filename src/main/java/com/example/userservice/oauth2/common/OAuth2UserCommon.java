package com.example.userservice.oauth2.common;

import java.util.Map;

public interface OAuth2UserCommon {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}

package com.okta.spring.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigurableAccessTokenConverter extends DefaultAccessTokenConverter {

    private final String scopeClaim;
    private final String rolesClaim;

    public ConfigurableAccessTokenConverter(String scopeClaim, String rolesClaim) {
        this.scopeClaim = scopeClaim;
        this.rolesClaim = rolesClaim;
    }

    private Map<String, ?> tweakScopeMap(Map<String, ?> map) {
        Map<String, Object> tokenMap = new LinkedHashMap<>(map);
        if (tokenMap.containsKey(scopeClaim)) {
            Object scope = tokenMap.get(scopeClaim);
            tokenMap.put(OAuth2AccessToken.SCOPE, scope);
        }

        if (tokenMap.containsKey(rolesClaim)) {
            Object roles = tokenMap.get(rolesClaim);
            tokenMap.put(UserAuthenticationConverter.AUTHORITIES, roles);
        }

        return tokenMap;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, tweakScopeMap(map));
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return super.extractAuthentication(tweakScopeMap(map));
    }

}

package com.okta.spring.oauth2;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

//@EnableResourceServer
//@EnableWebSecurity
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("#{ @environment['okta.oauth2.issuer'] ?: 'https://dev-259824.oktapreview.com/oauth2/ausar5cbq5TRRsbcJ0h7' }")
    protected String issuerUrl;

    @Value("#{ @environment['okta.oauth2.audience'] ?: 'all' }")
    protected String audience;

    @Value("#{ @environment['okta.oauth2.scopeClaim'] ?: 'scp' }")
    protected String scopeClaim;

    @Value("#{ @environment['okta.oauth2.rolesClaim'] ?: 'groups' }")
    protected String rolesClaim;

    @Autowired
    private Environment env;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                    .authorizeRequests()
                        .anyRequest()
                        .permitAll();
        // @formatter:on
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.resourceId(audience); // set audience
        config.tokenServices(tokenServices());
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore() {

        return new JwkTokenStore(issuerUrl + "/v1/keys", accessTokenConverter(), jwtClaimsSetVerifier());
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setAccessTokenConverter(new DefaultAccessTokenConverter() {

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

        });
        return jwtAccessTokenConverter;

    }

    @Bean
    @ConditionalOnMissingBean
    public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
        try {
            return new IssuerClaimVerifier(new URL(issuerUrl));
        } catch (MalformedURLException e) {
            throw new InvalidPropertyException(JwtClaimsSetVerifier.class, "okta.oauth2.issuer", "Failed to parse issuer URL", e);
        }
    }



}

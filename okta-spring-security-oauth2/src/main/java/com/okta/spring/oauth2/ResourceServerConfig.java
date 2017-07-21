package com.okta.spring.oauth2;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.net.MalformedURLException;
import java.net.URL;

@EnableResourceServer
@EnableWebSecurity
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
        jwtAccessTokenConverter.setAccessTokenConverter(new ConfigurableAccessTokenConverter(scopeClaim, rolesClaim));
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

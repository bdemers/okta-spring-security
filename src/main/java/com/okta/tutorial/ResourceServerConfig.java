package com.okta.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.lang.reflect.Field;
import java.security.KeyPair;
import java.util.Map;

@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private Environment env;



    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().authorizeRequests()
                .anyRequest().permitAll();
        // .requestMatchers().antMatchers("/foos/**","/bars/**")
        // .and()
        // .authorizeRequests()
        // .antMatchers(HttpMethod.GET,"/foos/**").access("#oauth2.hasScope('foo')
        // and #oauth2.hasScope('read')")
        // .antMatchers(HttpMethod.POST,"/foos/**").access("#oauth2.hasScope('foo')
        // and #oauth2.hasScope('write')")
        // .antMatchers(HttpMethod.GET,"/bars/**").access("#oauth2.hasScope('bar')
        // and #oauth2.hasScope('read')")
        // .antMatchers(HttpMethod.POST,"/bars/**").access("#oauth2.hasScope('bar')
        // and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
        ;
        // @formatter:on
    }

    // Remote token service

//    @Primary
//    @Bean
//    public RemoteTokenServices tokenServices() {
//        final RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setCheckTokenEndpointUrl("https://dev-259824.oktapreview.com/oauth2/ausar5cbq5TRRsbcJ0h7/v1/introspect");
//        tokenService.setClientId("fooClientIdPassword");
//        tokenService.setClientSecret("secret");
//        return tokenService;
//    }


    // JWT token store

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.resourceId("all");
        config.tokenServices(tokenServices());
    }

    /*
    @Bean
    public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    // converter.setSigningKey("123");
    final Resource resource = new ClassPathResource("public.txt");
    String publicKey = null;
    try {
        publicKey = IOUtils.toString(resource.getInputStream());
    } catch (final IOException e) {
        throw new RuntimeException(e);
    }
    converter.setVerifierKey(publicKey);
    return converter;
    }
    */
    @Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {

        JwkTokenStore tokenStore = new JwkTokenStore("https://dev-259824.oktapreview.com/oauth2/ausar5cbq5TRRsbcJ0h7/v1/keys");
        return tokenStore;
    }
//
//    @Bean
//    @Primary
//    ResourceServerTokenServices tokenServices() {
//
//
//        return new ResourceServerTokenServices() {
//
//            @Override
//            public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
//                return null;
//            }
//
//            @Override
//            public OAuth2AccessToken readAccessToken(String accessToken) {
//                return null;
//            }
//        }
//    }

}

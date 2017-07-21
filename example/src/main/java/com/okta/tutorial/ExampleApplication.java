package com.okta.tutorial;

import com.okta.tutorial.dao.DefaultStormtrooperDao;
import com.okta.tutorial.dao.StormtrooperDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ExampleApplication {

    @Bean
    protected StormtrooperDao stormtrooperDao() {
        return new DefaultStormtrooperDao();
    }

    @Bean
    protected GlobalMethodSecurityConfiguration methodSecurityConfiguration() {
        return new GlobalMethodSecurityConfiguration() {
            @Override
            protected MethodSecurityExpressionHandler createExpressionHandler() {
                return new OAuth2MethodSecurityExpressionHandler();
            }
        };
    }

//    @Override
//    public void configure(final HttpSecurity http) throws Exception {
//        // @formatter:off
//        http.sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                    .authorizeRequests()
//                        .anyRequest()
//                        .permitAll();
//        // @formatter:on
//    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}

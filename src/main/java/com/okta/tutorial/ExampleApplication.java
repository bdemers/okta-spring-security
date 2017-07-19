package com.okta.tutorial;

import com.okta.tutorial.dao.DefaultStormtrooperDao;
import com.okta.tutorial.dao.StormtrooperDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExampleApplication {

    @Bean
    protected StormtrooperDao stormtrooperDao() {
        return new DefaultStormtrooperDao();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:4200")
//                        .allowedHeaders("Authorization", "Content-Type")
//                        .exposedHeaders("Authorization", "Content-Type")
//                        .allowedMethods("GET", "PUT", "DELETE")
//                        .allowCredentials(false).maxAge(3600);
//            }
//        };
//    }
}

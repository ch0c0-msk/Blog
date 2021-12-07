package com.bestposts.blog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    @Bean
    public ThymeleafConfig springSecurityDialect(){
        return new ThymeleafConfig();
    }
}

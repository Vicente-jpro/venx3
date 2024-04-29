package com.adicionatec.venx3.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
        	.authorizeHttpRequests(auth -> {
        		auth.requestMatchers("/items/").permitAll()
        			.requestMatchers("/company/").permitAll();
        		auth.anyRequest().authenticated();
        	});
        return http.build();
    }

  
}
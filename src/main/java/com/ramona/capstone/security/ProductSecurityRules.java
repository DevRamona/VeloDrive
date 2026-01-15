package com.ramona.capstone.security;

import com.ramona.capstone.models.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ProductSecurityRules implements SecurityRules{
    public void configure (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/product/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/product/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/product/**").hasRole(Role.ADMIN.name());
    }
}

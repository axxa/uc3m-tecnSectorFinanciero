package com.practica12.cloud.config_server;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.*;

@EnableWebSecurity
public class WebSecurityConfig {

    // @formatter:off
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("practica12")
            .password("mysecret")
            .roles("USER")
            .build();
        return  new InMemoryUserDetailsManager(user);
    }
    
    // @formatter:on
}
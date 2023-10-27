package com.example.security.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors()
                .and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/appUsers/**")
                .hasAnyAuthority("USER", "ADMIN")
                .and().formLogin().disable()
//                .and()
                .build();
    }

}

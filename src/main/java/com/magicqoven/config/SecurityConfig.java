package com.magicqoven.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/bigquery/**", "/check-session", "/login").permitAll()
//                        .anyRequest().authenticated()
//                );
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/bigquery/*", "/check-session", "login").permitAll()
                        .anyRequest()
                );
//                        .ignoringRequestMatchers("/bigquery/*", "/check-session", "login"))
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                );
//                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}


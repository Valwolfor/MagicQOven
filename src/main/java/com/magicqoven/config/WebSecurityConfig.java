package com.magicqoven.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/bigquery/**", "/check-session", "/login").permitAll()
//                        .anyRequest().authenticated()
//                );
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/bigquery/*", "/check-session", "login").permitAll()
                        .anyRequest().authenticated()
                );
//                ).csrf(AbstractHttpConfigurer::disable);
//                        .ignoringRequestMatchers("/bigquery/*", "/check-session", "login"))
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                );
//                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}

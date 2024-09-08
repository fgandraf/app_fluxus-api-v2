package com.felipegandra.app_fluxusapiv2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RequestFilter requestFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

//                        // USERS
//                        .requestMatchers(HttpMethod.POST, "/v2/users/register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/v2/users/login").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/v2/users/update-info").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.PUT, "/v2/users/activate").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/v2/users/deactivate").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/v2/users/upgrade-permission").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/v2/users/downgrade-permission").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/v2/users/id").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/v2/users/email").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/v2/users").hasRole("ADMIN")
//
//                        // BANK BRANCHES
//                        .requestMatchers(HttpMethod.POST, "/v2/bank-branches").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/v2/bank-branches").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/v2/bank-branches").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET, "/v2/bank-branches/{id}").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.DELETE, "/v2/bank-branches").hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
                .addFilterBefore(
                        requestFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

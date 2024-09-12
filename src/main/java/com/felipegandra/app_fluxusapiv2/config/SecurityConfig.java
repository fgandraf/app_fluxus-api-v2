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

                                    // BRANCH
                                    .requestMatchers(HttpMethod.GET, "/v2/branches").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/branches/{id}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/branches/contacts/{branchId}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/branches").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/branches").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/v2/branches").hasRole("ADMIN")

                                    // INVOICES
                                    .requestMatchers(HttpMethod.GET, "/v2/invoices").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/invoices/description/{id}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/invoices").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/invoices/totals").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/v2/invoices").hasRole("ADMIN")

                                    // ORDER
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/flow").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/cities").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/done-to-invoice").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/filtered/{filter}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/invoiced/{invoiceId}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/professionals/{invoiceId}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/orders/{id}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/orders").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.PUT, "/v2/orders").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.DELETE, "/v2/orders").hasAnyRole("ADMIN", "USER")

                                    // PROFESSIONALS
                                    .requestMatchers(HttpMethod.GET, "/v2/professionals").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/professionals/tag-name-id").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/professionals/{id}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/professionals").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/professionals").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/v2/professionals").hasRole("ADMIN")

                                    // PROFILE
                                    .requestMatchers(HttpMethod.GET, "/v2/profiles").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/profiles/logo").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/profiles/to-print").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/profiles/trading-name").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/profiles").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/profiles").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/profiles/logo").hasRole("ADMIN")

                                    // SERVICES
                                    .requestMatchers(HttpMethod.GET, "/v2/services").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/services/{id}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/services").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/services").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/v2/services").hasRole("ADMIN")

                                    // USERS
                                    .requestMatchers(HttpMethod.POST, "/v2/users/login").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/v2/users/username/{username}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/users/professional/{professionalId}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.POST, "/v2/users/register").permitAll()
                                    .requestMatchers(HttpMethod.PUT, "/v2/users/update-info").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.PUT, "/v2/users/update-config").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/users/activate/{id}").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/users/deactivate/{id}").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/users/upgrade-permission/{id}").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/v2/users/downgrade-permission/{id}").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.GET, "/v2/users/{id}").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers(HttpMethod.GET, "/v2/users").hasRole("ADMIN")
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

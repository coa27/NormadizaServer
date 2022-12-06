package com.coa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JWTAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .exceptionHandling( exceptionhandling -> exceptionhandling.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> {
                    auth.antMatchers("/auth/**").permitAll();
                    auth.antMatchers(HttpMethod.GET, "/usuario").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.DELETE, "/usuario/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/tarea").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/tarea/{id}").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/tablero/tableros").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/tablero/{id}").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/tablero/usuario/{id}").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                });

        return httpSecurity.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(){
        return new JWTAuthorizationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}

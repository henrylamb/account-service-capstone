package com.adp.config;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.adp.util.JWTHelper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${rsa.public-key}")
  private RSAPublicKey publicKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/").permitAll()
            .requestMatchers("/registration").permitAll()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/users/manager/{id}").permitAll()
            .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                            .decoder(JWTHelper.jwtDecoder(publicKey)) // Use NimbusJwtDecoder with public key
                            .jwtAuthenticationConverter(JWTHelper.jwtAuthenticationConverter()))) // Extract roles

            .build();
  }

}

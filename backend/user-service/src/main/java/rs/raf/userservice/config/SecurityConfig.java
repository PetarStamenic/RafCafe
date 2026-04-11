package rs.raf.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import rs.raf.userservice.security.CustomUserService;
import rs.raf.userservice.security.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final CustomUserService customUserService;

    public SecurityConfig(JWTFilter jwtFilter, CustomUserService customUserService) {
        this.jwtFilter = jwtFilter;
        this.customUserService = customUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/v1/users").permitAll()
                .requestMatchers("/api/v1/users/login").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

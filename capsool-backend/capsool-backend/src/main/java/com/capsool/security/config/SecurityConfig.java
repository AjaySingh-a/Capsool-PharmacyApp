package com.capsool.security.config;

import com.capsool.repository.UserRepository;
import com.capsool.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.capsool.security.jwt.JwtAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/pharmacy/**").hasAuthority("ROLE_PHARMACY_OWNER")
                        .requestMatchers("/user/profile").authenticated()
                        .requestMatchers("/user/pharmacy-dashboard").hasAuthority("ROLE_PHARMACY_OWNER")

                        // ✅ Order API Access Control
                        // ✅ Order APIs (Only for Pharmacy Owners)
                        .requestMatchers("/order/pending").hasAuthority("ROLE_PHARMACY_OWNER")
                        .requestMatchers("/order/{orderId}/finalize").hasAuthority("ROLE_PHARMACY_OWNER")
                        .requestMatchers("/order/confirmed").authenticated()
                        .requestMatchers("/order/pharmacy/{pharmacyId}").hasAuthority("ROLE_PHARMACY_OWNER") // ✅ Pharmacy Owner can see won orders


                        // ✅ Bid API Access Control (NEW)
                        .requestMatchers("/bid/**").hasAuthority("ROLE_PHARMACY_OWNER")
                        .requestMatchers("/bid/place").hasAuthority("ROLE_PHARMACY_OWNER") // 🔥 Sirf Pharmacy Owner bid kar sakta hai
                        .requestMatchers("/bid/{orderId}").authenticated() // 🔥 Koi bhi logged-in user bids dekh sakta hai
                        .requestMatchers("/bid/finalize/{orderId}").hasAuthority("ROLE_PHARMACY_OWNER") // 🔥 Pharmacy Owner bid finalize karega



                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

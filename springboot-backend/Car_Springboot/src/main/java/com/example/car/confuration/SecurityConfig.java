package com.example.car.confuration;

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;  
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;  

import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.car.enums.UserRole;
import com.example.car.service.jwt.UserService;
import com.example.car.service.jwt.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.Arrays; 

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private  final UserService userService;
	
   
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userService = userService;
	}

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> request
                .requestMatchers("/api/authen/**").permitAll()  // Public access for auth endpoints
                .requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name())  // Restricted access for admin
                .requestMatchers("/api/customer/**").hasAnyAuthority(UserRole.CUSTOMER.name())  // Restricted access for customers
                .anyRequest().authenticated()  // All other requests require authentication
            )
            .sessionManagement(manager -> 
                manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No session management, JWT-based
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Custom JWT filter
        
        return http.build();
    }
    
	 @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
    @Bean  
    public AuthenticationProvider authenticationProvider() {  
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();  
        authProvider.setUserDetailsService(userService.userDetailsService());  
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());  
        return authProvider;  
    }


    @Bean  
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {  
        return config.getAuthenticationManager();  
    }  
	
  }

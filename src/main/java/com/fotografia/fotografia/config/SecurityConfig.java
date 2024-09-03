package com.fotografia.fotografia.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fotografia.fotografia.services.AdminDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
    public SecurityConfig(UserDetailsService userDetailsService, AdminDetailsService adminDetailsService) {
        
    }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
    .authorizeHttpRequests(authz -> authz
    .requestMatchers("/gallery/**").hasRole("ADMIN")
    .anyRequest().authenticated()
    )
    .formLogin(form -> form
    .loginPage("/login") // Puedes definir una página de inicio de sesión personalizada si lo deseas
    .permitAll()
)
.logout(logout -> logout
    .logoutUrl("/logout")
    .permitAll()
);
    return http.build();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  public WebMvcConfigurer corsConfigurer(CorsRegistry registry) {
    return new WebMvcConfigurer() {
        @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
    };
  }
}

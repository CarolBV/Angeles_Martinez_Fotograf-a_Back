package com.fotografia.fotografia.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fotografia.fotografia.services.AdminDetailsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;





@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig  {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.POST, "/admin/create").permitAll()
            .requestMatchers(HttpMethod.POST, "/admin/login").permitAll()
            .requestMatchers(HttpMethod.POST,"/gallery/image").permitAll()
            .anyRequest().authenticated())
            .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            
            
        
      return http.build();
}

@Bean
public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder,
AdminDetailsService adminDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(adminDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
}

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder(); // Asegúrate de usar BCrypt
}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
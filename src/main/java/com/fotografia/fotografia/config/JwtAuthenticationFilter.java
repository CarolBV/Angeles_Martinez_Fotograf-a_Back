package com.fotografia.fotografia.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fotografia.fotografia.security.JwtUtil;
import com.fotografia.fotografia.services.CustomAdminDetailsService;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomAdminDetailsService customAdminDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomAdminDetailsService customAdminDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customAdminDetailsService = customAdminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { // Aseguramos que IOException está correctamente declarada

        final String token = getTokenFromRequest(request);

        // Si el token es válido y no está vacío
        if (token != null && StringUtils.hasText(token)) {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = customAdminDetailsService.loadUserByUsername(username);

            // Validamos el token
            if (jwtUtil.validateToken(token, userDetails)) {
                // Creamos la autenticación
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecemos la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

        }

        // Continuamos con el filtro, esto debe estar siempre fuera del bloque if
        filterChain.doFilter(request, response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Se elimina la palabra 'Bearer ' del token
        }
        return null;

    }
}
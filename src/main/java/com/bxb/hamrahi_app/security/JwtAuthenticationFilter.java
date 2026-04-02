package com.bxb.hamrahi_app.security;

import com.bxb.hamrahi_app.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtAuthenticationToken is a Spring Security filter that intercepts incoming
 * HTTP requests to validate JWT tokens.
 * It checks the Authorization header for a Bearer token, validates it using
 * JwtUtil, and sets the authentication context if the token is valid.
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * This filter intercepts incoming HTTP requests to validate JWT tokens.
     * It checks the Authorization header for a Bearer token, validates it,
     * and sets the authentication context if the token is valid.
     */
    private final JwtUtil jwtUtil;

    /**
     * Intercepts each HTTP request to validate the JWT token.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the filter chain to pass the request and response
     *                   to the next filter
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an I/O error occurs during request processing
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException,
            IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            String userId = null;
            String jwtToken = null;
            String role = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);

                try {
                    String tokenType = jwtUtil.extractTokenType(jwtToken);
                    if ("REFRESH".equals(tokenType)) {
                        log.warn("Refresh token attempted to access API");

                        throw new InsufficientAuthenticationException(
                                "Refresh token cannot access APIs");
                    }

                    userId = jwtUtil.extractUserId(jwtToken);
                     role = jwtUtil.extractUserRole(jwtToken);
                } catch (Exception e) {
                    log.error("JWT parsing failed", e);
                    throw new BadCredentialsException("Invalid JWT token", e);
                }
            }

            if (userId != null && SecurityContextHolder
                    .getContext().getAuthentication() == null) {
                if (jwtUtil.isTokenValid(jwtToken, userId)) {

                    List<SimpleGrantedAuthority> authorities =
                            List.of(new SimpleGrantedAuthority(
                                    "ROLE_" + role));

                    UsernamePasswordAuthenticationToken authToken
                            = new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            authorities
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }
            }
        } catch (io.jsonwebtoken.JwtException e) {

            log.error("Invalid JWT token: {}", e.getMessage());

            throw new BadCredentialsException(
                    "Invalid or malformed JWT token", e);
        }
        filterChain.doFilter(request, response);
    }
}


package com.example.productinventory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private LogoutService logoutService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("JWT Filter executed for: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        // No Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header missing or invalid");

            filterChain.doFilter(request, response);
            return;
        }

        // Remove Bearer prefix
        String token = authHeader.substring(7);

        // Check whether token is blacklisted
        if (logoutService.isBlacklisted(token)) {
            System.out.println("JWT token is blacklisted");

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token has been revoked"
            );
            return;
        }

        try {
            String username = jwtService.extractUsername(token);

            System.out.println("Extracted username: " + username);

            if (username == null) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid token"
                );
                return;
            }

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            System.out.println("Loaded user: " + userDetails.getUsername());
            System.out.println("Authorities: " + userDetails.getAuthorities());

            if (!jwtService.isTokenValid(token, userDetails)) {
                System.out.println("JWT validation failed");

                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid or expired token"
                );
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContext context =
                    SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            System.out.println(
                    "Authentication successfully established for: " + username
            );

        } catch (Exception e) {
            System.out.println("JWT authentication failed");
            e.printStackTrace();

            SecurityContextHolder.clearContext();

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "JWT authentication failed"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}

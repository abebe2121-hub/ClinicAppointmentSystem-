
package com.clinic.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader =
                request.getHeader("Authorization");

        // No Bearer token: continue to Spring Security.
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        final String jwt =
                authorizationHeader.substring(7).trim();

        if (jwt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract username from the JWT.
            String username = jwtService.extractUsername(jwt);

            // Authenticate only if no authentication exists yet.
            if (username != null
                    && SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                // Validate token and account status.
                if (jwtService.isTokenValid(jwt, username)
                        && userDetails.isEnabled()) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

                    logger.debug("JWT authentication successful for: "
                            + username);

                } else {
                    logger.warn("JWT validation failed for user: "
                            + username);
                }
            }

        } catch (UsernameNotFoundException exception) {

            logger.warn("JWT user does not exist.");

        } catch (Exception exception) {

            // Never log the JWT or secret.
            logger.warn("JWT authentication failed: "
                    + exception.getClass().getSimpleName());
        }

        filterChain.doFilter(request, response);
    }
}

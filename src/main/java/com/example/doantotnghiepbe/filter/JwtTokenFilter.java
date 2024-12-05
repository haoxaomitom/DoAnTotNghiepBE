package com.example.doantotnghiepbe.filter;

import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtils;

    private static final String LOGIN_URL = "/app/components/Login/LoginAndRegister.html";

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (isByPassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no token is found, redirect to login page
            response.sendRedirect(LOGIN_URL);
            return;
        }

        final String token = authHeader.substring(7);
        if (token.isEmpty()) {
            // If token is empty, redirect to login page
            response.sendRedirect(LOGIN_URL);
            return;
        }

        try {
            final String username = jwtTokenUtils.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Users userDetails = (Users) userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    // If token is invalid, redirect to login page
                    response.sendRedirect(LOGIN_URL);
                    return;
                }
            }
        } catch (Exception e) {
            // If any exception occurs during token validation, redirect to login page
            response.sendRedirect(LOGIN_URL);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isByPassToken(@NotNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/users/login", "POST"),
                Pair.of("/users/register", "POST"),
                Pair.of("/api/posts", "GET"),
                Pair.of("/api/posts/countByDistrict", "GET"),
                Pair.of("/api/posts/user/", "GET"),
                Pair.of("/api/vnpay/return","GET"),
                Pair.of("/api/vnpay/payment/details/{txnRef}","GET"),
                Pair.of("/api/posts/{id}", "GET"),
                Pair.of("/api/comments/post/", "GET"),
                Pair.of("/api/comments/", "DELETE"),
                Pair.of("/api/users/verified","GET"),
                Pair.of("/api/reports", "POST"),
                Pair.of("/api/users/register", "POST"),
                Pair.of("api/posts/{postId}", "DELETE"),
                Pair.of("/api/posts/top","GET"),
                Pair.of("/api/contactInformation/create", "POST"),
                Pair.of("/api/upPosts/", "POST"),
                Pair.of("/api/amenities/", "POST"),
                Pair.of("/api/images/upload", "POST"),
                Pair.of("/api/amenities/", "GET"),
                Pair.of("/api/upPosts/create", "POST"),
                Pair.of("/api/upPosts", "POST"),
                Pair.of("/api/upPosts/images/", "POST")

        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> bypassToken : bypassTokens) {
            if (requestPath.contains(bypassToken.getLeft()) &&
                    requestMethod.equals(bypassToken.getRight())) {
                return true;
            }
        }
        return false;
    }
}
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
    private  UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request
            , @NotNull HttpServletResponse response
            , @NotNull FilterChain filterChain) throws ServletException, IOException {
        if(isByPassToken(request)) {
            filterChain.doFilter(request,response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
            return;
        }

        final  String token = authHeader.substring(7);
        final  String username = jwtTokenUtils.extractUsername(token);
        if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null){
            Users userDetails = (Users) userDetailsService.loadUserByUsername(username);
            if(jwtTokenUtils.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
    private boolean isByPassToken(@NotNull HttpServletRequest request) {

        final List<Pair<String,String>> bypassTokens = Arrays.asList(
                Pair.of("/users/login", "POST"),
                Pair.of("/users/register", "POST")
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


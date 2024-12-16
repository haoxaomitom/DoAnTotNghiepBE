package com.example.doantotnghiepbe.configurations;

import com.example.doantotnghiepbe.entity.Roles;
import com.example.doantotnghiepbe.filter.JwtTokenFilter;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests -> {
                    requests.requestMatchers(
                                    "/api/**",
                                    "/api/users/login",
                                    "/api/users/register",
                                    "/api/posts/{id}",
                                    "/api/comments/post/{postId}",
                                    "/api/comments/{commentId}",
                                    "/api/users/verified",
                                    "/api/contactInformation/**",
                                    "/api/administration/user/login",
                                    "/api/contactInformation/**",
                                    "/api/vnpay/return",
                                    "/api/vnpay/payment/details/{txnRef}",
                                    "/api/reports",
                                    "/api/posts/{postId}",
                                    "/api/contactInformation/create",
                                    "/api/updatePosts/",
                                    "/api/updatePosts/images/delete/",
                                    "/api/updatePosts/images/upload/",
                                    "/api/posts/",
                                    "/api/approval-posts",
                                    "/api/approval-posts/approve/",
                                    "/api/approval-posts/reject/",
                                    "/api/approval-posts/search",
                                    "/api/posts/admin/search/",
                                    "/admin/payments/all",
                                    "/api/prices/",
                                    "/api/prices",
                                    "/api/posts/delete/",
                                    "/api/updatePosts/updateImage/",
                            "/api/vnpay/user/",
                            "/api/vnpay/*",
                            "/api/posts/admin/search"
                            ).permitAll()
                            .requestMatchers("/api/users/getUserByUsername").hasAnyRole("USER", "ADMIN", "MODERATOR")
                            .requestMatchers("/api/contactInformation/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
                            .anyRequest().authenticated();
                }))
                .formLogin(form -> form
                        .loginPage("/app/components/Login/LoginAndRegister.html")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable);

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }

}

package ca.gbc.comp3095.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {}) // Nez: Use the CORS rules defined in CorsConfig.
                .authorizeExchange(exchanges -> exchanges
                        //Allow browser preflight requests so CORS checks can complete before the real request.
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Keep auth and public documentation endpoints accessible without a JWT.
                        .pathMatchers(
                                "/api/auth/**",
                                "/api/v1/auth/**",
                                "/actuator/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/*/v3/api-docs/**",
                                "/api/v1/courses/health"
                        ).permitAll()

                        //Require authentication for all remaining routes.
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()) //Validate JWT access tokens at the gateway level.
                .build();
    }
}
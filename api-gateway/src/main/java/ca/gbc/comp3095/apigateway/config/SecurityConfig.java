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
                .cors(cors -> {}) // uses CorsConfig
                .authorizeExchange(exchanges -> exchanges

                        // Nez: allow preflight requests
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .pathMatchers(
                                "/api/auth/**",
                                "/api/v1/auth/**",
                                "/actuator/**",

                                // Swagger UI
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",

                                // Gateway’s own OpenAPI
                                "/v3/api-docs/**",

                                // Proxied OpenAPI from services (aggregation)
                                "/*/v3/api-docs/**",

                                "/api/v1/courses/health"
                        ).permitAll()

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt())
                .build();
    }
}
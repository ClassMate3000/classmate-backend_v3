package ca.gbc.comp3095.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /*
     ============================================================
     PRODUCTION CONFIG — OAuth2 / JWT (Keycloak)
     Commented out for demo deployment on Render Free tier
     ============================================================

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {})
                .authorizeExchange(exchanges -> exchanges
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
                                //  removed problematic code

     "/api/v1/courses/health"
     ).permitAll()
     .anyExchange().authenticated()
     )
     .oauth2ResourceServer(oauth2 -> oauth2.jwt())
     .build();
     }
     */

    /*
     ============================================================
     DEMO CONFIG — Permit All (No Authentication)
     Safe for presentations and free hosting environments
     ============================================================
    */

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {})
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll()   //  Allow everything
                )
                .build(); //  No OAuth2 / JWT
    }
}
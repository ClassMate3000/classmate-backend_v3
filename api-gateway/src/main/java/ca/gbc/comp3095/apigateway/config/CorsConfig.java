package ca.gbc.comp3095.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Nez: Allow requests from the local frontend development server.
        config.setAllowedOrigins(List.of("http://localhost:5173"));

        //Allow the HTTP methods used by the frontend, including OPTIONS for browser preflight checks.
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow request headers commonly sent by the frontend such as Content-Type and Authorization.
        config.setAllowedHeaders(List.of("*"));

        // Allow credentials if cookies or authenticated cross-origin requests are needed.
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Apply this CORS configuration to every route exposed by the gateway.
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
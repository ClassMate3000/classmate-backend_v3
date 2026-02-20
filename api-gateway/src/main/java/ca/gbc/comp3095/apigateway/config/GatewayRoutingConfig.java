package ca.gbc.comp3095.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Course Service
                .route("course-service", r -> r.path("/api/v1/courses/**")
                        // PENNY // .filters(f -> f.stripPrefix(3)) // JAMES - <-- FIX
                        .uri("https://classmate3000-course-service.onrender.com/api/v1"))
                        // .uri("http://course-service:8080")) // PENNY - used docker dns name instead of host.docker.internal

                // CourseProgress Service
                .route("courseprogress-service", r -> r.path("/api/v1/course-progress/**")
                        // PENNY // .filters(f -> f.stripPrefix(3)) // JAMES - <-- FIX
                        .uri("https://classmate3000-courseprogress-service.onrender.com/api/v1"))
                        // .uri("http://courseprogress-service:8081"))  // PENNY - used docker dns name instead of host.docker.internal

                // Reminder Service
                .route("reminder-service", r -> r.path("/api/v1/reminders/**")
                        // PENNY // .filters(f -> f.stripPrefix(3)) // JAMES - <-- FIX
                        .uri("https://classmate3000-reminder-service.onrender.com/api/v1"))
                        // .uri("http://reminder-service:8082"))  // PENNY - used docker dns name instead of host.docker.internal
 
                // Task Service
                .route("task-service", r -> r.path("/api/v1/tasks/**")
                        // PENNY // .filters(f -> f.stripPrefix(3)) // JAMES - <-- FIX
                        .uri("https://classmate3000-task-service.onrender.com/api/v1"))
                        // .uri("http://task-service:8083"))  // PENNY - used docker dns name instead of host.docker.internal

                // PENNY - User Auth endpoints
                .route("user-service-auth", r -> r.path("/api/v1/auth/**")
                        .uri("https://classmate3000-user-service.onrender.com/api/v1"))
                        // .uri("http://user-service:8089"))

                // PENNY - Optional: protected demo endpoint (yours is /api/protected/**)
                .route("user-service-protected", r -> r.path("/api/protected/**")
                        .uri("https://classmate3000-user-service.onrender.com/api/v1"))
                        // .uri("http://user-service:8089")) // PENNY - used docker dns name instead of host.docker.internal
                
                // PENNY - 
                // ---- Swagger docs passthrough ----
                .route("course-service-docs", r -> r.path("/course-service/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("https://classmate3000-course-service.onrender.com/api/v1"))
                        // .uri("http://course-service:8080"))

                .route("courseprogress-service-docs", r -> r.path("/courseprogress-service/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("https://classmate3000-courseprogress-service.onrender.com/api/v1"))
                        // .uri("http://courseprogress-service:8081"))

                .route("task-service-docs", r -> r.path("/task-service/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("https://classmate3000-task-service.onrender.com/api/v1"))
                        // .uri("http://task-service:8083"))

                .route("reminder-service-docs", r -> r.path("/reminder-service/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("https://classmate3000-reminder-service.onrender.com/api/v1"))
                        // .uri("http://reminder-service:8082"))

                .route("user-service-docs", r -> r.path("/user-service/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("https://classmate3000-user-service.onrender.com/api/v1"))
                        // .uri("http://user-service:8089"))

                .build();
    }

}
package ca.gbc.comp3095.apigateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorizationHeaderFilterTest {

    @Test
    void filterAddsAuthorizationHeader() {
        AuthorizationHeaderFilter filter = new AuthorizationHeaderFilter();

        MockServerHttpRequest request = MockServerHttpRequest.get("/test")
                .header("Authorization", "Bearer mock-token")
                .build();

        ServerWebExchange exchange = MockServerWebExchange.from(request);

        GatewayFilterChain chain = exchange1 -> {
            String header = exchange1.getRequest().getHeaders().getFirst("Authorization");
            assertEquals("Bearer mock-token", header);
            return Mono.empty();
        };

        filter.filter(exchange, chain).block();
    }

    @Test
    void filterHandlesMissingAuthorizationHeader() {
        AuthorizationHeaderFilter filter = new AuthorizationHeaderFilter();

        MockServerHttpRequest request = MockServerHttpRequest.get("/test").build();

        ServerWebExchange exchange = MockServerWebExchange.from(request);

        GatewayFilterChain chain = exchange1 -> {
            String header = exchange1.getRequest().getHeaders().getFirst("Authorization");
            assertEquals(null, header);
            return Mono.empty();
        };

        filter.filter(exchange, chain).block();
    }
}
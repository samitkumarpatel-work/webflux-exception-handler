package com.example.webfluxexceptionhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class WebfluxExceptionHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxExceptionHandlerApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions
				.route()
				.path("/person", builder -> builder
						.GET("",request -> {
							var x = 10 / 0; 
							return ServerResponse.ok().build();
						}))
//				.onError(RuntimeException.class, (e, request) -> ServerResponse.badRequest().build())
//				.onError(ArithmeticException.class, (e, request) -> ServerResponse.badRequest().build())
				.build();
	}


}

// This handle all the exceptions
@Component
class GlobalErrorAttributes extends DefaultErrorAttributes {
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request,
												  ErrorAttributeOptions options) {
		Map<String, Object> map = super.getErrorAttributes(request, options);
//		map.put("status", 400);
//		map.put("message", "username is required");
		map.put("text", "This is a custom error message");
		return map;
	}
}

package com.foodapp.security;


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.http.HttpMethod;
import java.util.Arrays;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public MapReactiveUserDetailsService userDetailsService() {

	    UserDetails admin =
	            User.withUsername("admin")
	                    .password("{noop}admin123")
	                    .roles("ADMIN")
	                    .build();

	    UserDetails user =
	            User.withUsername("user")
	                    .password("{noop}user123")
	                    .roles("USER")
	                    .build();
	    
	    UserDetails user1 =
	            User.withUsername("ananya")
	                    .password("{noop}user123")
	                    .roles("USER")
	                    .build();
	    UserDetails user2 =
	            User.withUsername("khushbu")
	                    .password("{noop}user123")
	                    .roles("USER")
	                    .build();
	    UserDetails user3 =
	            User.withUsername("isheeta")
	                    .password("{noop}user123")
	                    .roles("USER")
	                    .build();
	    

	    return new MapReactiveUserDetailsService(
	            admin,
	            user, user1,user2,user3
	    );
	}
	
	@Bean
	public SecurityWebFilterChain securityFilterChain(
	        ServerHttpSecurity http) {

	    return http
	            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	            .csrf(ServerHttpSecurity.CsrfSpec::disable)

	            .authorizeExchange(exchanges -> exchanges
	            		
	            		.pathMatchers(HttpMethod.OPTIONS,"/**")
	            		.permitAll()

	            		
	            		.pathMatchers("/me")
	            		.authenticated()
	            		
	            		.pathMatchers("/users/all")
	            		.hasRole("ADMIN")

	            		.pathMatchers("/orders/allOrders")
	            		.hasRole("ADMIN")

	            		.pathMatchers("/admin/all")
	            		.hasRole("ADMIN")

	            		.pathMatchers(HttpMethod.GET, "/admin/**")
	            		.hasAnyRole("USER", "ADMIN")

	                    .pathMatchers("/admin/**")
	                    .hasRole("ADMIN")

	                    .pathMatchers("/users/**")
	                    .hasAnyRole("USER", "ADMIN")

	                    .pathMatchers("/orders/**")
	                    .hasAnyRole("USER", "ADMIN")

	                    .anyExchange()
	                    .authenticated()
	            )

	            .httpBasic(Customizer.withDefaults())

	            .exceptionHandling(exceptions ->
	                exceptions.authenticationEntryPoint(
	                    (exchange, ex) -> {

	                        exchange.getResponse()
	                                .setStatusCode(HttpStatus.UNAUTHORIZED);

	                        return exchange.getResponse()
	                                .setComplete();
	                    }
	                )
	            )
	            .build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"));
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
	

	


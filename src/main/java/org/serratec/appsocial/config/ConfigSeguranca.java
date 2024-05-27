package org.serratec.appsocial.config;

import java.util.Arrays;

import org.serratec.appsocial.security.JwtAuthenticationFilter;
import org.serratec.appsocial.security.JwtAuthorizationFilter;
import org.serratec.appsocial.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ConfigSeguranca {

	@Autowired
	UserDetailsService userDetailsService;
	

	@Autowired
	JwtUtil jwtUtil;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors((cors) -> cors.configurationSource(corsConfigurationSource()))
				.httpBasic(Customizer.withDefaults()).authorizeHttpRequests(requests -> {
					
					/*
					 * requests.requestMatchers("http://localhost:8080/swagger-ui/index.html").
					 * permitAll(); requests.requestMatchers("/swagger-ui/**").permitAll();
					 * requests.requestMatchers("/swagger-ui/").permitAll();
					 * requests.requestMatchers("/swagger-ui/index.html").permitAll();
					 * requests.requestMatchers("/v3/api-docs").permitAll();
					 */
					
					
					 requests.requestMatchers("/swagger-ui/**").permitAll();
					 requests.requestMatchers("/swagger-resources/**").permitAll();
					 requests.requestMatchers("/v3/api-docs").permitAll();
					 requests.requestMatchers("/swagger-ui.html/").permitAll();
					 
					
					
					
					requests.requestMatchers(HttpMethod.GET, "/login").permitAll();
					requests.requestMatchers(HttpMethod.GET, "/usuarios").authenticated();
					requests.requestMatchers(HttpMethod.GET, "/usuarios/{id}").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/usuarios").authenticated();
					requests.requestMatchers(HttpMethod.PUT, "/usuarios/{id}/atualizarDados").authenticated();
					requests.requestMatchers(HttpMethod.PUT, "/usuarios/{id}/atualizarSenha").authenticated();
					requests.requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").authenticated();
					
					requests.requestMatchers(HttpMethod.GET, "/postagens").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/postagens").authenticated();
					requests.requestMatchers(HttpMethod.PUT, "/postagens/{id}").authenticated();
					requests.requestMatchers(HttpMethod.DELETE, "/postagens/{id}").authenticated();
					
					requests.requestMatchers(HttpMethod.GET, "/comentarios").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/comentarios").authenticated();
					requests.requestMatchers(HttpMethod.PUT, "/comentarios/{id}").authenticated();
					requests.requestMatchers(HttpMethod.DELETE, "/comentarios/{id}").authenticated();
					
					requests.requestMatchers(HttpMethod.POST, "/relacionamentos/{idSeguir}/seguir/{id}").authenticated();
                    requests.requestMatchers(HttpMethod.DELETE, "/relacionamentos/{idSeguir}/deixarDeSeguir/{id}").authenticated();
					

				}).sessionManagement(session -> {
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				});

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		

		http.addFilter(jwtAuthenticationFilter);
		http.addFilter(new JwtAuthorizationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil,
				userDetailsService));

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());

		return source;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

 
	// comentario

}
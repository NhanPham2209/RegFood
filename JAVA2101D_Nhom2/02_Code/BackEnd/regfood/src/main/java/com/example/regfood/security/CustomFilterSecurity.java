package com.example.regfood.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;




@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {
	 @Autowired
	    CustomUserDetailService customUserDetailService;

	    @Autowired
	    CustomFiterJwt customFiterJwt;

	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{
	        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
	        authenticationManagerBuilder.userDetailsService(customUserDetailService);
	        return authenticationManagerBuilder.build();


	    }
	    
//	    @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//	    	http
//			.authorizeHttpRequests((requests) -> requests
//				.requestMatchers("/login/**").permitAll()
//				.anyRequest().authenticated()
//			)
//			.formLogin((form) -> form
//				.loginPage("login/signin")
//				.permitAll()
//			)
//			.logout((logout) -> logout.permitAll());
//	    http.addFilterBefore(customFiterJwt, UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	    }
	    
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

	       http. 
	       	cors().disable()
	       	.csrf().disable()
	       	.authorizeRequests()
            .requestMatchers("/login/**","/index/**","/promotion/**",
            					"/category/**","/restaurant/**","/post/**","/sales/**")
	       	.permitAll()
	       	.anyRequest()
	       	.authenticated()
	       	.and()
	       	.httpBasic()
	       	;
	       http.cors();
	       http.addFilterBefore(customFiterJwt, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }
//	    "/user/**",
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    	
	    }
	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("*"));
	        configuration.setAllowedHeaders(Arrays.asList("*"));
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	
}

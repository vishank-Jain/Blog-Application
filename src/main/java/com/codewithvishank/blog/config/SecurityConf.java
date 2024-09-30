package com.codewithvishank.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codewithvishank.blog.security.CustomerUserDetailService;
//import com.codewithvishank.blog.security.JwtAuthenticationEntryPoint;
//import com.codewithvishank.blog.security.JwtAuthenticationFilter;
import com.codewithvishank.blog.security.JwtAuthenticationEntryPoint;
import com.codewithvishank.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConf {
	@Autowired
	private CustomerUserDetailService customerUserDetailService;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeHttpRequests()
	        .requestMatchers("/h2-console/**").permitAll()  // Allow access to H2 console
	        .requestMatchers("/api/auth/login").permitAll() // Allow login endpoint
	        .anyRequest().authenticated()
	        .and().headers().frameOptions().sameOrigin() // Allow H2 console to be served in a frame
	        .and().exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
	        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	    

		http.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain defaultsecuritychain = http.build();
		return defaultsecuritychain;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customerUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;

	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
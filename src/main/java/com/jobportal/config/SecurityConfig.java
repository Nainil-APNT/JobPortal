package com.jobportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jobportal.user.JwtAuthFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	public static final String[] PUBLIC_URLS = {

			"/v2/api-docs", "/swagger-ui.html", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**" };

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/products/new","/products/authenticate").permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/products/**")
//                .authenticated().and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/user/welcome", "/user/new", "/user/authenticate").permitAll()
						.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers("/applicant/create").permitAll()
						.requestMatchers("/applicationdetails/create").permitAll()
						.requestMatchers("/company/create").permitAll()
						.requestMatchers("/employee/create").permitAll()
						.requestMatchers("/jobcategory/create").permitAll()
						.requestMatchers("/jobinformation/create").permitAll()
						.requestMatchers("/joblocation/create").permitAll()
			
						// .requestMatchers(HttpMethod.GET).permitAll()
			
						.requestMatchers("/applicant/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
						.requestMatchers("/applicationdetails/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
						.requestMatchers("/company/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
						.requestMatchers("/employee/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
						.requestMatchers("/jobcategory/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
						.requestMatchers("/jobinformation/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
						.requestMatchers("/joblocation/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}

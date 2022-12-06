package com.digiswit.exercise1.confederation.app.security;

import static com.digiswit.exercise1.confederation.app.security.SecurityConstants.LOGIN_URL;
//import static com.digiswit.exercise1.confederation.app.security.SecurityConstants.CLUB_URL;
//import static com.digiswit.exercise1.confederation.app.security.SecurityConstants.SWAGGER_UI;
import static com.digiswit.exercise1.confederation.app.security.SecurityConstants.LOGIN_URL_HEROKU;
import static com.digiswit.exercise1.confederation.app.security.SecurityConstants.CLUB_URL_HEROKU;
import static com.digiswit.exercise1.confederation.app.security.SecurityConstants.SWAGGER_UI_HEROKU;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		 * - Disable cookies
		 * - Enable CORS using default values
		 * - Disable CSRF
		 * - No authentication required for login
		 * - The rests of end-points must be securized
		 */	
		
		httpSecurity
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.cors().and()
			.csrf().disable()
			.authorizeRequests().antMatchers(HttpMethod.POST, LOGIN_URL_HEROKU).permitAll()
			.and()
			.authorizeRequests().antMatchers(HttpMethod.POST, CLUB_URL_HEROKU).permitAll()
			.and()
			.authorizeRequests().antMatchers(SWAGGER_UI_HEROKU).permitAll()
			.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()));
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
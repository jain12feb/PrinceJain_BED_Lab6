package com.gl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	PasswordEncoder myPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider myAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(myPasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(requests -> requests.requestMatchers("/", "/register", "/css/**", "/js/**").permitAll()
						.requestMatchers("/home").hasAnyAuthority("USER", "ADMIN")
						.requestMatchers("/add-student-form", "/add-student", "/delete/{id}", "/update/{id}",
								"/update-student")
						.hasAuthority("ADMIN").anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/home")
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll())
				.exceptionHandling(handling -> handling.accessDeniedPage("/403")).cors(cors -> cors.disable())
				.csrf(csrf -> csrf.disable()).authenticationProvider(myAuthenticationProvider()).build();
	}

}

package com.example.carros.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // usado para habilitar a segurança por ROLES
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
//			.formLogin().and()
			.httpBasic()
			.and().csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//		auth
//			.inMemoryAuthentication().passwordEncoder(passwordEncoder)
//			.withUser("user").password( passwordEncoder.encode("user") ).roles("USER")
//			.and()
//			.withUser("admin").password( passwordEncoder.encode("admin") ).roles("USER", "ADMIN");
	}
}

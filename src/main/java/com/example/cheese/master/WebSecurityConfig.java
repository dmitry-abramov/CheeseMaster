package com.example.cheese.master;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterAfter(new LoginPageFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(
				(requests) -> requests
					.requestMatchers("/", "/home", "/error").permitAll()
					.requestMatchers("/css/**").permitAll()
					.requestMatchers(PathRequest.toH2Console()).permitAll()
					.anyRequest().authenticated())
				.csrf(csrf -> csrf
						.ignoringRequestMatchers(PathRequest.toH2Console()))
				.headers(headers -> headers
						.frameOptions(options -> options
								.sameOrigin()))			
				.formLogin((form) -> form
						.loginPage("/login")
						.defaultSuccessUrl("/cheese")						
						.permitAll())
				.logout((logout) -> logout
						.permitAll());

		return http.build();
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}

	@Bean
	public UserDetailsService jdbcUserDetailsService(DataSource dataSource, PasswordEncoder encoder) {

		UserDetails user = User
				.withUsername("admin")
				.password(encoder.encode("store"))
				.roles("USER_ROLE")
				.build();

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.createUser(user);
		return users;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
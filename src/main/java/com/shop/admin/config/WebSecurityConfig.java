package com.shop.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	private ShopUserDetailsService shopUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
		 AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		 authBuilder.userDetailsService(shopUserDetailsService).passwordEncoder(passwordEncoder);
		 return authBuilder.build();
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
         .csrf(csrf -> csrf.disable())  // Disable CSRF protection
         .authorizeHttpRequests( authorizeRequests -> authorizeRequests	
        	.requestMatchers("/register","/session-expired","/session-invalid").permitAll()
        	.requestMatchers("/users/**","/channa/**", "/settings/**", "/countries/**","/states/**").hasAuthority("Admin")
        	.requestMatchers("/countries/**","/states/**").hasAuthority("Sale")
		 	//.requestMatchers("/users","/users/**", "/channa/**", "/settings/**", "/countries/**","/states/**").hasRole("Admin")
		 	//.requestMatchers("/user/**").hasRole("USER")
		 	.requestMatchers("/resources/**","/js/**","/webjars/**").permitAll()  // Allow access to login page and static resources
            .anyRequest().authenticated()
        )
         
        .formLogin(formLogin -> formLogin
        		.loginPage("/login") // Specify the custom login page
        		.usernameParameter("email")
        		.loginProcessingUrl("/perform_login")// URL that processes the login form
        		.defaultSuccessUrl("/", true)  // Redirect after successful login
        		.failureUrl("/login?error=true")  // Redirect after failed login
        		.permitAll()  // Allow everyone to see the login page
        )
        
        .sessionManagement(
       		 session -> session
       		   .maximumSessions(1)
       		 .maxSessionsPreventsLogin(true) // Prevents new logins if session limit is reached
       		 .expiredUrl("/session-expired")
       		 .sessionRegistry(sessionRegistry())
       		  // Migrates the session on successful login
       	// .invalidSessionUrl("/session-invalid")
       )
        
     // Session fixation protection
//        .sessionManagement(session -> session
//            .sessionFixation().migrateSession()
//        )
        
        
        // Handle invalid sessions
        .sessionManagement(session -> session
            .invalidSessionUrl("/session-invalid")
        )
        
        
        .logout(logout -> logout
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/login?logout=true")
                .permitAll()
         );
		return http.build();
	}
	
	 @Bean
	    public SessionRegistry sessionRegistry() {
	        return new SessionRegistryImpl();
	    }
	 
	// Used to publish session events like session creation, destruction
//	    @Bean
//	    public HttpSessionEventPublisher httpSessionEventPublisher() {
//	        return new HttpSessionEventPublisher();
//	    }

}

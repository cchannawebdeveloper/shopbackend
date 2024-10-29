package com.shop.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private ShopUserDetailsService userDetailsService;
	
	@Autowired
    private ShopAuthenticationSuccessHandler successHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    
   @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
   @Bean
   public DaoAuthenticationProvider authenticationProvider() {
	   DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	   authProvider.setUserDetailsService(userDetailsService);
	   authProvider.setPasswordEncoder(passwordEncoder());
	   return authProvider;
	   
   }
	
//	@Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
//			AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//			authBuilder.userDetailsService(shopUserDetailsService);
//			return authBuilder.build();
//    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
		 .authenticationProvider(authenticationProvider())
        // .csrf(csrf -> csrf)  // Disable CSRF protection//For API need to disable
         .authorizeHttpRequests( auth -> auth	
        	//.requestMatchers("/session-expired","/register","/session-expired","/session-invalid").permitAll()
        		 
        	.requestMatchers("/user-photos/**","/images/**","/fontawesome/**","/css/**", "/js/**", "/webjars/**").permitAll()
        	//.requestMatchers("/users/**","/channa/**", "/settings/**", "/countries/**","/states/**").hasAuthority("Admin")
        	//.requestMatchers("/countries/**","/states/**").hasAuthority("Sale")
		 	//.requestMatchers("/users","/users/**", "/channa/**", "/settings/**", "/countries/**","/states/**").hasRole("Admin")
		 	//.requestMatchers("/user/**").hasRole("USER")
		 	//.requestMatchers("/js/**","/images/**","/fontawesome/**","/css/**","/js/**","/webjars/**").permitAll()  // Allow access to login page and static resources
            .anyRequest().authenticated()
        )
         
        .formLogin(formLogin -> formLogin
        		.loginPage("/login") // Specify the custom login page
        		.usernameParameter("email")
        		.loginProcessingUrl("/perform_login")// URL that processes the login form
        		.successHandler(successHandler)
        	//	.defaultSuccessUrl("/", true)  // Redirect after successful login
        	//	.failureUrl("/login?error=true")  // Redirect after failed login
        		.permitAll()  // Allow everyone to see the login page
        )
        
        .sessionManagement(
         		 (session) -> session
         		 //.invalidSessionUrl("/login?sessionExpired=true")
         		 .maximumSessions(1)
         		// .maxSessionsPreventsLogin(false) // Prevents new logins if session limit is reached
         		// .expiredUrl("/session-expired")
         		// .expiredUrl("/login?expired=true")
         	//	 .sessionRegistry(this.sessionRegistry())
         )
        
        .logout(logout -> logout
        		 .logoutUrl("/logout")
        		 .logoutSuccessUrl("/login?logout=true")
        		 .invalidateHttpSession(true)
                .permitAll())
        
        
        
        
        .rememberMe(
        		rem -> rem
        		.key("channa93444uttUYYY8tt")
        		.tokenValiditySeconds(7 * 24 * 60 * 60)
        	//	.userDetailsService(userDetailsService)
        		)
        
        ;
		return http.build();
	}
	
	 @Bean
	   public HttpSessionEventPublisher httpSessionEventPublisher() {
	        return new HttpSessionEventPublisher();
	    }
	
	//addResourceHandlers
	 
	  @Bean
	    public SpringResourceTemplateResolver templateResolver() {
	        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	        templateResolver.setPrefix("classpath:/templates/");
	        templateResolver.setSuffix(".html");
	        templateResolver.setTemplateMode(TemplateMode.HTML);
	        templateResolver.setCharacterEncoding("UTF-8");
	        templateResolver.setCacheable(false);  // For development, disable cache
	        return templateResolver;
	    }

	    @Bean
	    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver);
	        templateEngine.addDialect(new SpringSecurityDialect());
	        return templateEngine;
	    }
	   
	    
	 
	

}

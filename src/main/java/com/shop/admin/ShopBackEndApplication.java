package com.shop.admin;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import com.shop.admin.config.ShopUserDetails;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ShopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopBackEndApplication.class, args);
	}
	
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> {
            // Get the current authenticated user
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof ShopUserDetails) {
                    return Optional.of(((ShopUserDetails) principal).getUsername());
                }
                return Optional.of(principal.toString());
            }
            return Optional.empty();
        };
		
		
		//return null;
	}

}

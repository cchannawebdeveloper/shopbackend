package com.shop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {
	
	
	
	@GetMapping("/session-expired")
    public String sessionExpired() {
		System.out.println("sessionExpired work!!!!!!!");
        return "session-expired";  // This refers to the Thymeleaf template (session-expired.html)
    }
	
	 @GetMapping("/session-invalid")
	    public String sessionInvalid(Model model) {
		 System.out.println("sessionInvalid work!!!!!!!");
	        model.addAttribute("message", "Your session is invalid or has expired.");
	        return "session-invalid"; // Points to session-invalid.html
	    }
	
	

}

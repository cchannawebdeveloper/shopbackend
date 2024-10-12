package com.shop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Docker and Thymeleaf!");
        return "homepage/index";
    }
 	
 	@GetMapping("/user22")
 	public String user() {
 		return "user";
 	}
	

}

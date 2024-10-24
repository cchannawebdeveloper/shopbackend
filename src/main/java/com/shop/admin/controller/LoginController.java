package com.shop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping()
    public String login(Model model) {
		model.addAttribute("pageTitle","Log In - Shop Admin");
        return "login/login";
    }

}

package com.shop.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.admin.model.User;
import com.shop.admin.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = userService.listAll();
		model.addAttribute("pageTitle","Users - Shop Admin");
		model.addAttribute("listUsers", listUsers);
		return "user/users";
	}

}

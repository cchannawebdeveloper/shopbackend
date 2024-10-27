package com.shop.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.admin.exception.UserNotFoundException;
import com.shop.admin.model.Role;
import com.shop.admin.model.User;
import com.shop.admin.service.UserService;
import com.shop.admin.util.FileUploadUtil;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public String listAll(Model model) {
		List<User> listUsers = userService.listAll();
		model.addAttribute("pageTitle","Users - Shop Admin");
		model.addAttribute("listUsers", listUsers);
		return "user/users";
	}
	
	@GetMapping("/new")
	public String newUser(Model model) {
		List<Role> listRoles = userService.listRoles();
		User user=new User();
		model.addAttribute("listRoles",listRoles);
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("pageTitle", "Add new user");
		return "user/user_form";
	}
	
	@PostMapping("/save")
	public String saveUser(
			User user
			, RedirectAttributes attributes
			, @RequestParam("image") MultipartFile multipartFile
			) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saveUser =  userService.saveUser(user);
			String uploadDir = "user-photos/"+saveUser.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		return "redirect:/user/users";
	}
	
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id
			,Model model
			,RedirectAttributes attributes) {
		try {
			User user = userService.get(id);
			List<Role> listRoles = userService.listRoles();
			// Create a map of attributes to add
	        Map<String, Object> att = new HashMap<>();
	        att.put("user", user);
	        att.put("pageTitle", "Edit User with id: "+id);
	        att.put("listRoles", listRoles);
			// Add all attributes to the model
	        model.addAllAttributes(att);
			return "user/user_form";
		} catch (UserNotFoundException ex) {
			attributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}	
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id
			,RedirectAttributes rda) {
		try {
			userService.delete(id);
			rda.addFlashAttribute("message", "The user id "+ id + " has been deleted succesfully!");
		} catch (UserNotFoundException ex) {
			rda.addFlashAttribute("message", ex.getMessage());
			
		}
		return "redirect:/users";	
	}

}

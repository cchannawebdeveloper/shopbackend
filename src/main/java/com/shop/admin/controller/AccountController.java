package com.shop.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.admin.config.ShopUserDetails;
import com.shop.admin.model.User;
import com.shop.admin.service.UserService;
import com.shop.admin.util.FileUploadUtil;

@Controller
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/account")
	public String viewUserDetails(
			@AuthenticationPrincipal ShopUserDetails loggedUser,
			Model model
			) {
		String email = loggedUser.getUsername();
		User user = userService.getByEmail(email);
		model.addAttribute("user", user);
		return "user/account_form";
	}
	
	@PostMapping("/account/update")
	public String saveDetails(User user
			, RedirectAttributes attributes
			, @AuthenticationPrincipal ShopUserDetails loggedUser
			, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saveUser =  userService.updateAccount(user);
			String uploadDir = "user-photos/"+saveUser.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} 
		else {
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			userService.updateAccount(user);
		}
		
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		attributes.addFlashAttribute("message","Your account details have been updated.");
		return "redirect:/account";
	}
	
	

}

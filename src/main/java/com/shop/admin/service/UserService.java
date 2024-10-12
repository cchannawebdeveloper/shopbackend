package com.shop.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.admin.dao.UserRepository;
import com.shop.admin.model.User;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRepo;
	
	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}

}

package com.shop.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.admin.dao.UserSessionRepository;

@Service
public class UserSessionService {
	
	@Autowired
	private UserSessionRepository userSession;
	

}

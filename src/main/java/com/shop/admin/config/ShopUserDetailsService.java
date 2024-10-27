package com.shop.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.admin.dao.UserRepository;
import com.shop.admin.model.User;

@Service
public class ShopUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if(user != null ) {	
			 return new ShopUserDetails(user);
		}
		throw new UsernameNotFoundException("Could not find username with email: "+email);
	}

}

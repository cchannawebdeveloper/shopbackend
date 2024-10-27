package com.shop.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.admin.dao.RoleRepository;
import com.shop.admin.dao.UserRepository;
import com.shop.admin.dao.UserSessionRepository;
import com.shop.admin.exception.UserNotFoundException;
import com.shop.admin.model.Role;
import com.shop.admin.model.User;
import com.shop.admin.model.UserSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	
	 @Autowired
	 private UserSessionRepository userSessionRepository;
	 
	 @Autowired 
	 private RoleRepository roleRepo;
	
	 @Autowired
	 private HttpServletRequest request;

	 @Autowired
	 private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}
	
	public User updateAccount(User userInform) {
		User userInDB = userRepo.findById(userInform.getId()).get();
		if(!userInform.getPassword().isEmpty()) {
			userInDB.setPassword(userInform.getPassword());
			encodePassword(userInDB);
		}
		
		if(userInform.getPhotos() != null) {
			userInDB.setPhotos(userInform.getPhotos());
		}
		
		userInDB.setFirstName(userInform.getFirstName());
		userInDB.setLastName(userInform.getLastName());
		
		
		return userRepo.save(userInDB);
	}
	
	public void encodePassword(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}
	
	public User saveUser(User user) {
		return null;
	}
	
	
	public User get(Integer id) throws UserNotFoundException {
			
			try {
			return userRepo.findById(id).get();
			} catch (NoSuchElementException ex) {
				throw new UserNotFoundException("Could not find any user");
			}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user "+ id);
		}
		userRepo.deleteById(id);
		
	}
	
	public User login(String username, String password) throws AuthenticationException {
        User user = userRepo.getUserByEmail(username);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Check if the user already has an active session
            if (userSessionRepository.existsByUserId(user.getId())) {
                throw new IllegalStateException("User is already logged in.");
            }

            // Get the current session
            HttpSession session = request.getSession(); // Create session if it doesn't exist
            String sessionId = session.getId(); // Get the session ID

            // Save the user session
            userSessionRepository.save(new UserSession(user.getId(), sessionId));
            return user;
        }
        throw new AuthenticationException("Invalid credentials.");
    }

}

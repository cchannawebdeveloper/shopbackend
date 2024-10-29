package com.shop.admin.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.shop.admin.dao.UserRepository;
import com.shop.admin.dao.UserSessionRepository;
import com.shop.admin.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ShopAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	 @Autowired
	 private UserSessionRepository userSessionRepository;
	 
	 
	 @Autowired
	 private UserRepository userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Retrieve the current session
        //HttpSession session = request.getSession();
       // String sessionId = session.getId(); // Get the session ID
        
        // Get the user ID from the authentication object
        // int userId = ((ShopUserDetails) authentication.getPrincipal()).getId();// Customize according to your UserDetails implementation
        // You can store the session ID in your user session repository or use it as needed
        // Create a new UserSession entity
       // UserSession userSession = new UserSession();
        ////userSession.setUserId(userId);
       // userSession.setSessionId(sessionId);

        // Save the user session to the database
      //  userSessionRepository.save(userSession);
		
		String email = authentication.getName();
		User user = userRepo.getUserByEmail(email);
		
		if ( user != null ) {
			user.setLastLogin(LocalDateTime.now());
			userRepo.save(user);
		}

        // Redirect to the default target URL after successful login
        response.sendRedirect("/ShopAdmin");
		
	}

}

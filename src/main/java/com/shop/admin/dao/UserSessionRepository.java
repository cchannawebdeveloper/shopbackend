package com.shop.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.admin.model.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
	
	 boolean existsByUserId(Integer userId);
	 
	 void deleteBySessionId(String sessionId);

}

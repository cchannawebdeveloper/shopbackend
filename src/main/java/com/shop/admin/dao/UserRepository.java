package com.shop.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.admin.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	// Custom update query using JPQL
	@Modifying
	@Transactional // Required for modifying queries
	@Query("UPDATE User u SET u.enabled =?2 WHERE u.id = ?1")
	public void updateEnableStatus(Integer id, Boolean enabled);

}

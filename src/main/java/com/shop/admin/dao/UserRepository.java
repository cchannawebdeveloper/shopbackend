package com.shop.admin.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import com.shop.admin.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	
	
	// Custom update query using JPQL
	@Modifying
	@Transactional // Required for modifying queries
	@Query("UPDATE User u SET u.enabled = :enabled, u.lastModifiedBy = :lastModifiedBy, u.lastModifiedDate = :lastModifiedDate WHERE u.id = :id")
	void updateEnableStatus(@Param("id") Integer id, 
							@Param("enabled") Boolean enabled,
							@Param("lastModifiedBy") String lastModifiedBy,
							@Param("lastModifiedDate") LocalDateTime lastModifiedDate);
	
	//:id
	//@Query("UPDATE User u SET u.enabled =?2 WHERE u.id = ?1")

}

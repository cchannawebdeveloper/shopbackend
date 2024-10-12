package com.shop.admin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shop.admin.dao.RoleRepository;
import com.shop.admin.model.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository repo;
	
//	@Test
//	public void testCreateFirstRole() {	
//		Role roleAdmin=new Role("Admin","manage everything");
//		Role saveRole = repo.save(roleAdmin);	
//		assertThat(saveRole.getId()).isGreaterThan(0);
//		
//	}
	
	@Test
	public void testCreateRestRole() {	
		Role roleSalesperson=new Role("Salesperson","manage product price,"
				+ " customers, shipping, order and sales report");
		
		Role roleEditor=new Role("Editor","manage categories, brands,"
				+ " products, articles and menus");
		
		
		Role roleShipper=new Role("Shipper","view products, view orders"
				+ " and update order status");
		
		Role roleAssistant=new Role("Assistant","manage questions and reviews");
		
		List<Role> listRole = List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant);
		 repo.saveAll(listRole);
		
		
		//Role saveRole = repo.save(roleEditor);	
		
		
		//assertThat(saveRole.getId()).isGreaterThan(0);
		
	}
	
	

}

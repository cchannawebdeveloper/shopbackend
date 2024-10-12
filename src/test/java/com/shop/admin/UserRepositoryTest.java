package com.shop.admin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shop.admin.dao.UserRepository;
import com.shop.admin.model.Role;
import com.shop.admin.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 3);
		User userChanna = new User("channa", "yoeurn", "cchannayoeurng@gmail.com", "Channa17");
		userChanna.addRole(roleAdmin);
		User saveUser = repo.save(userChanna);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		User userSokchan = new User("sokchan", "Hi", "sokchan@gmail.com", "Sokchan17");
		
		Role roleSale = entityManager.find(Role.class, 33);
		Role roleAssistant = entityManager.find(Role.class, 36);
		
		userSokchan.addRole(roleSale);
		userSokchan.addRole(roleAssistant);
		
		User saveUser = repo.save(userSokchan);
		assertThat(saveUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(usr -> System.out.println(usr));
	}

}

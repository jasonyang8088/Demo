package demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import demo.zss.Application;
import demo.zss.entity.user.Authority;
import demo.zss.entity.user.Role;
import demo.zss.entity.user.User;
import demo.zss.repository.AuthorityRepository;
import demo.zss.repository.RoleRepository;
import demo.zss.repository.UserRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Test
	@Rollback(false)
	public void addUser(){
		User user =new User();
		user.setUsername("admin");
		user.setPassword("admin");
		Role role = new Role();
		role.setName("admin");
		Authority authority= new Authority();
		authority.setName("admin");
		authority.setAuth("/admin");
		authorityRepository.save(authority);
		roleRepository.save(role);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
	}
}

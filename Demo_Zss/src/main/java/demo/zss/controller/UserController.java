package demo.zss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.zss.entity.user.User;
import demo.zss.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/users")
	public List<User> findAll(){
		return userRepository.findAll();
	}
}

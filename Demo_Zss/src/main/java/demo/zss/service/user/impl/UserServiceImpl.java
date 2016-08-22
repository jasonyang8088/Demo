package demo.zss.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import demo.zss.entity.user.User;
import demo.zss.repository.UserRepository;
import demo.zss.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Cacheable(value = "users",keyGenerator = "wiselyKeyGenerator")  
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}

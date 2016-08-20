package demo.zss.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.zss.entity.user.User;
import demo.zss.repository.AuthorityRepository;
import demo.zss.repository.UserRepository;

@Service
public class WebUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;

	private Logger logger = LoggerFactory.getLogger(WebUserDetailsService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        if (user == null) {
        	throw new UsernameNotFoundException("No such user: " + username);
        } else if (user.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " has no authorities");
        }
        UserDetailsAdapter userDetails = new UserDetailsAdapter(user,authorityRepository);
        logger.info("Login user:  "+userDetails.getUsername());
        return userDetails;
	}

}

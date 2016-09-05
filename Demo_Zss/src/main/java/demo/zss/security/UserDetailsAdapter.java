package demo.zss.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import demo.zss.entity.user.Authority;
import demo.zss.entity.user.Role;
import demo.zss.entity.user.User;
import demo.zss.repository.AuthorityRepository;

public class UserDetailsAdapter implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(UserDetailsAdapter.class);

	private AuthorityRepository authorityRepository;

	private User user;

	public UserDetailsAdapter(User user,AuthorityRepository authorityRepository) {
		this.user = user;
		this.authorityRepository=authorityRepository;
	}

	public Long getId() {
		return user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (Role r : user.getRoles()) {
			List<Authority> authList = r.getAuthorities();
			if (r.getName().equals("admin")) {
				logger.info("this is 1");
				authList = authorityRepository.findAll();
			} else {
				logger.info("this is 2");
				authList = r.getAuthorities();
			}
			if (authList != null) {
				for (Authority auth : authList) {
					authorities.add(new SimpleGrantedAuthority("ROLE_"+auth.getAuth()));
				}
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

//package demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//		http
//			.authorizeRequests()
//				.antMatchers("/css/**", "/js/**").permitAll()		
//				.antMatchers("/user/**").hasRole("USER")
//				.anyRequest().authenticated()
//				.and()
//			.formLogin()
//				.loginPage("/login").permitAll()
//				.failureUrl("/login-error");	
//		// @formatter:on
//	}
//	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		// @formatter:off
////		auth
////			.inMemoryAuthentication()
////				.withUser("user").password("password").roles("USER");
//		//自定义UserDetailService
//		auth
//			.userDetailsService(userDetailsService)
//			.passwordEncoder(new BCryptPasswordEncoder());
//		// @formatter:on
//	}
//	
//
//}

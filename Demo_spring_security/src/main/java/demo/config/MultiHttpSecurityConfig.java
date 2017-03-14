package demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {
	
// 如果用内存管理，而没有UserDetailsService的实现类，下面方法可以执行
//	@Bean
//	public UserDetailsService userDetailsService() throws Exception {
//		// @formatter:off
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("user").password("password").roles("USER").build());
//		manager.createUser(User.withUsername("admin").password("password").roles("USER","ADMIN").build());
//		return manager;
//		// @formatter:on
//	}

	@Configuration
	@Order(1)                                                        //1 Create an instance of WebSecurityConfigurerAdapter that contains @Order to specify which WebSecurityConfigurerAdapter should be considered first.
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.antMatcher("/api/**")                               //2 The http.antMatcher states that this HttpSecurity will only be applicable to URLs that start with /api/
				.authorizeRequests()
					.anyRequest().hasRole("ADMIN")
					.and()
				.httpBasic();
			// @formatter:on
		}
	}

	//3 Create another instance of WebSecurityConfigurerAdapter. 
	//  If the URL does not start with /api/ this configuration will be used.
	//  This configuration is considered after ApiWebSecurityConfigurationAdapter since it has an @Order value after 1 (no @Order defaults to last).
	@Configuration        							
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.anyRequest().authenticated()
					.and()
				.formLogin();
			// @formatter:on
		}
	}
}

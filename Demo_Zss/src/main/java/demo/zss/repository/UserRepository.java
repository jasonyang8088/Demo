package demo.zss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}

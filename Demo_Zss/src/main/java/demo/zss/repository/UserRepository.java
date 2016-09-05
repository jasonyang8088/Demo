package demo.zss.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.zss.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	@Modifying
	@Transactional
	@Query("update User u set u.status=0 where u.id=?1")
	void deleteUser(Long id);


}

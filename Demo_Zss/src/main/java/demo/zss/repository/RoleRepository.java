package demo.zss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	List<Role> findByNameNot(String string);


	
}

package demo.zss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	
}

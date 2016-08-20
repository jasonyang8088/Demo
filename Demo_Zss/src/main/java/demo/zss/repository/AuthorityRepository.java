package demo.zss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.user.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}

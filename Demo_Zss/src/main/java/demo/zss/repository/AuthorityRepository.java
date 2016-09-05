package demo.zss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import demo.zss.entity.user.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>,PagingAndSortingRepository<Authority, Long>{

	List<Authority> findByNameNot(String string);

}

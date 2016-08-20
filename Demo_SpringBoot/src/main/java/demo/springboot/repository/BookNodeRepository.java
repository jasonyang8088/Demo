package demo.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.springboot.entity.BookNode;

public interface BookNodeRepository extends JpaRepository<BookNode, Long>{

	List<BookNode> findByBookId(long id);

	List<BookNode> findByBookIdAndDepth(long id, byte depth);

	

}

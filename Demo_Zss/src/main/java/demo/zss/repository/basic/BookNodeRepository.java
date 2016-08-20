package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.BookNode;

public interface BookNodeRepository extends JpaRepository<BookNode, Long> {

}

package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.TextBook;

public interface TextBookRepository extends JpaRepository<TextBook, Long> {

}

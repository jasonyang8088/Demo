package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import demo.zss.entity.basic.BookNode;

public interface BookNodeRepository extends JpaRepository<BookNode, Long>,JpaSpecificationExecutor<BookNode> {

	List<BookNode> findByBookId(Long textBookId);

	BookNode findByBookIdAndNameAndDepth(Long id, String stringCellValue, byte b);

	BookNode findByBookIdAndName(Long id, String stringCellValue);

	List<BookNode> findByBookIdAndDepth(Long id, byte b);

	List<BookNode> findByBookIdOrderByOrderNo(Long textBookId);

}

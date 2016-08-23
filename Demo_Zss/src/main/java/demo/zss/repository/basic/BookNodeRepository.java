package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import demo.zss.entity.basic.BookNode;

public interface BookNodeRepository extends PagingAndSortingRepository<BookNode, Long>,JpaSpecificationExecutor<BookNode> {

	List<BookNode> findByBookId(Long textBookId);

	BookNode findByBookIdAndNameAndDepth(Long id, String stringCellValue, byte b);

	BookNode findByBookIdAndName(Long id, String stringCellValue);

}

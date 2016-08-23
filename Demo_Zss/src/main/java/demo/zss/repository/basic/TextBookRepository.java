package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import demo.zss.entity.basic.TextBook;

public interface TextBookRepository extends PagingAndSortingRepository<TextBook, Long>,JpaSpecificationExecutor<TextBook> {

	List<TextBook> findByVersionId(Long versionId);


}

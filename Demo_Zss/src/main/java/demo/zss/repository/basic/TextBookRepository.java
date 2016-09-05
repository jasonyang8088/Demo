package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.zss.entity.basic.TextBook;

public interface TextBookRepository extends JpaRepository<TextBook, Long>,JpaSpecificationExecutor<TextBook> {

	List<TextBook> findByVersionId(Long versionId);

	@Modifying
	@Query("update #{#entityName} t set t.status=0 where t.id=?1")
	void deleteTextBook(Long id);

	@Modifying
	@Query("update #{#entityName} t set t.status=1 where t.id=?1")
	void activeTextBook(Long id);
	


}

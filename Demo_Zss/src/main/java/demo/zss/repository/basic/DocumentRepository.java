package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import demo.zss.entity.basic.Document;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Long>,JpaSpecificationExecutor<Document> {

}

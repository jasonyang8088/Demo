package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import demo.zss.entity.basic.DocumentSpecialSpec;

public interface DocumentSpecialSpecRepository extends PagingAndSortingRepository<DocumentSpecialSpec, Long>,JpaSpecificationExecutor<DocumentSpecialSpec> {


}

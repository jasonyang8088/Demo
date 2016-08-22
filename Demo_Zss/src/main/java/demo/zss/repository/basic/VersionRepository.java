package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import demo.zss.entity.basic.Version;

public interface VersionRepository extends PagingAndSortingRepository<Version, Long>,JpaSpecificationExecutor<Version> {

}

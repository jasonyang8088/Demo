package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.zss.entity.basic.Version;

public interface VersionRepository extends JpaRepository<Version, Long>,JpaSpecificationExecutor<Version> {

	List<Version> findBySubjectId(Long id);

	@Modifying
	@Query("update #{#entityName} v set v.status=0 where v.id=?1")
	void deleteVersion(Long id);

	@Modifying
	@Query("update #{#entityName} v set v.status=1 where v.id=?1")
	void activeVersion(Long id);

}

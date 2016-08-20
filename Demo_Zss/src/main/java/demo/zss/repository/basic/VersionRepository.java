package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {

}

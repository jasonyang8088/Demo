package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	List<Subject> findByStage(Byte stage);

}

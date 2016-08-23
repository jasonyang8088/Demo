package demo.zss.repository.basic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.BasicKnowledgePoint;

public interface BasicKnowledgePointRepository extends JpaRepository<BasicKnowledgePoint, Long>{

	BasicKnowledgePoint findBySubjectIdAndName(Long id, String stringCellValue);

	List<BasicKnowledgePoint> findBySubjectId(Long subjectId);

}

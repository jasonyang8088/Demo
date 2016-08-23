package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.KnowledgePoint;

public interface KnowledgePointRepository extends JpaRepository<KnowledgePoint, Long> {

	KnowledgePoint findByBookNodeIdAndNameAndDepth(Long id, String stringCellValue, Byte depth);

}

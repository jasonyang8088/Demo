package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.BasicKnowledgePoint;

public interface BasicKnowledgePointRepository extends JpaRepository<BasicKnowledgePoint, Long>{

}

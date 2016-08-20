package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

}

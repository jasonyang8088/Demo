package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	List<Subject> findByStage(Byte stage);

}

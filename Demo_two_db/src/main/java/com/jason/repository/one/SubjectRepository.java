package com.jason.repository.one;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jason.entity.one.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}

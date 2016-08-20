package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Subject;
import com.demo.repository.SubjectRepository;

@RestController
public class SubjectController {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@RequestMapping("stage/{stage}/subjects")
	public List<Subject> findByStage(@PathVariable("stage") Byte stage){
		return subjectRepository.findByStage(stage);
	}

}

package com.jason.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jason.entity.one.Subject;
import com.jason.entity.two.Subject2;
import com.jason.repository.one.SubjectRepository;
import com.jason.repository.two.Subject2Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubjectTest {

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private Subject2Repository subject2Repository;
	
	@Test
	public void getTest(){
		Subject sub=subjectRepository.findOne(411L);
		System.out.println(sub.getName());
	}
	
	
	@Test
	public void getTest2(){
		Subject2 sub=subject2Repository.findOne(411L);
		System.out.println(sub.getName());
	}
}

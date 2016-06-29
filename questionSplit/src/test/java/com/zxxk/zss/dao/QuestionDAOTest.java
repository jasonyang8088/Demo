package com.zxxk.zss.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zxxk.zss.Application;
import com.zxxk.zss.entity.Question;
import com.zxxk.zss.entity.question.QuestionJuniorMath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class QuestionDAOTest {
	
	@Autowired
	private QuestionDAO questionDAO;

	@Test
	@Rollback(false)
	public void saveTest(){
		Question q = new QuestionJuniorMath();
		q.setStem("hahaha");
		questionDAO.save(q);
	}
}

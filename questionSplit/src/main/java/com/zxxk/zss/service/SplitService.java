package com.zxxk.zss.service;

import java.util.List;

import com.zxxk.zss.entity.Question;

public interface SplitService {

	public List<Question> splitQuestion(List<String> lstContent);
	
}

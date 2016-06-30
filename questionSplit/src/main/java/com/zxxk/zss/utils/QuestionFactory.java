package com.zxxk.zss.utils;

import com.zxxk.zss.entity.Question;
import com.zxxk.zss.entity.question.QuestionJuniorBiology;
import com.zxxk.zss.entity.question.QuestionJuniorChemistry;
import com.zxxk.zss.entity.question.QuestionJuniorChinese;
import com.zxxk.zss.entity.question.QuestionJuniorEnglish;
import com.zxxk.zss.entity.question.QuestionJuniorGeography;
import com.zxxk.zss.entity.question.QuestionJuniorHistory;
import com.zxxk.zss.entity.question.QuestionJuniorMath;
import com.zxxk.zss.entity.question.QuestionJuniorPhysics;
import com.zxxk.zss.entity.question.QuestionJuniorPolitics;
import com.zxxk.zss.entity.question.QuestionPrimaryChinese;
import com.zxxk.zss.entity.question.QuestionPrimaryEnglish;
import com.zxxk.zss.entity.question.QuestionPrimaryMath;
import com.zxxk.zss.entity.question.QuestionSeniorBiology;
import com.zxxk.zss.entity.question.QuestionSeniorChemistry;
import com.zxxk.zss.entity.question.QuestionSeniorChinese;
import com.zxxk.zss.entity.question.QuestionSeniorEnglish;
import com.zxxk.zss.entity.question.QuestionSeniorGeography;
import com.zxxk.zss.entity.question.QuestionSeniorHistory;
import com.zxxk.zss.entity.question.QuestionSeniorMath;
import com.zxxk.zss.entity.question.QuestionSeniorPhysics;
import com.zxxk.zss.entity.question.QuestionSeniorPolitics;

public class QuestionFactory {
	
	static Question q ;
	
	public static Question createQuestion(String stage,String subjectName){
		switch(stage){
		case "1":
			if(subjectName.equals("语文"))q=new QuestionPrimaryChinese();
			if(subjectName.equals("数学"))q=new QuestionPrimaryMath();
			if(subjectName.equals("英语"))q=new QuestionPrimaryEnglish();
			break;
		case "2":
			if(subjectName.equals("语文"))q=new QuestionJuniorChinese();
			if(subjectName.equals("数学"))q=new QuestionJuniorMath();
			if(subjectName.equals("英语"))q=new QuestionJuniorEnglish();
			if(subjectName.equals("物理"))q=new QuestionJuniorPhysics();
			if(subjectName.equals("化学"))q=new QuestionJuniorChemistry();
			if(subjectName.equals("生物"))q=new QuestionJuniorBiology();
			if(subjectName.equals("政治"))q=new QuestionJuniorPolitics();
			if(subjectName.equals("历史"))q=new QuestionJuniorHistory();
			if(subjectName.equals("地理"))q=new QuestionJuniorGeography();
			break;
		case "3":
			if(subjectName.equals("语文"))q=new QuestionSeniorChinese();
			if(subjectName.equals("数学"))q=new QuestionSeniorMath();
			if(subjectName.equals("英语"))q=new QuestionSeniorEnglish();
			if(subjectName.equals("物理"))q=new QuestionSeniorPhysics();
			if(subjectName.equals("化学"))q=new QuestionSeniorChemistry();
			if(subjectName.equals("生物"))q=new QuestionSeniorBiology();
			if(subjectName.equals("政治"))q=new QuestionSeniorPolitics();
			if(subjectName.equals("历史"))q=new QuestionSeniorHistory();
			if(subjectName.equals("地理"))q=new QuestionSeniorGeography();
		}
		return q;
	}

}

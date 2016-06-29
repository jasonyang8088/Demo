package com.zxxk.zss.entity.question;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.zxxk.zss.entity.Question;
import com.zxxk.zss.utils.Constants;

/***
 * 高中语文
 */
@Entity
@Table(name=Constants.TABLE_QUESTION_SENIOR_CHINESE)
public class QuestionSeniorChinese extends Question {

}

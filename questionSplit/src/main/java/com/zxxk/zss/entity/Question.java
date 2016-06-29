package com.zxxk.zss.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Question {

	private Integer qid; 	// 试题ID
	private String type; 	// 试题类型
	private String stem; 	// 试题题干
	private String options;	// 试题选项
	private String answer; 	// 试题答案
	private String analyse;	// 试题解析
	private String status; 	// 试题状态
	private Integer pid; 	// 对应材料试题的ID
	private String difficult;
	private String fenzhi;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getQid() {
		return qid;
	}

	public String getType() {
		return type;
	}

	@Lob
	public String getStem() {
		return stem;
	}
	
	@Lob
	public String getOptions() {
		return options;
	}
	
	@Lob
	public String getAnswer() {
		return answer;
	}
	
	@Lob
	public String getAnalyse() {
		return analyse;
	}

	public String getStatus() {
		return status;
	}

	public Integer getPid() {
		return pid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getDifficult() {
		return difficult;
	}

	public String getFenzhi() {
		return fenzhi;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public void setFenzhi(String fenzhi) {
		this.fenzhi = fenzhi;
	}

}

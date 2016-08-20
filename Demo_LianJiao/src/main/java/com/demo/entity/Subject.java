package com.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="demo_subject")
public class Subject {

	private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
	private Byte stage;
	private @Column(length=6,name="subject_name") String subjectName;
	
	@OneToMany(mappedBy="subject")
	private List<Version> versions;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getStage() {
		return stage;
	}
	public void setStage(Byte stage) {
		this.stage = stage;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public List<Version> getVersions() {
		return versions;
	}
	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}
	
	
	
}

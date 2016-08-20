package com.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="demo_document")
public class Document {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long FId;
	
	private @ManyToOne @JsonIgnore BookNode node;
	
	private String fileName;
	
	private Integer fileSize;
	
	private String fileSourceType;
	
	private Integer views;
	
	private Date CreateTime;
	
	private String content;
	
	private Byte FRescCatagory;

	public Long getFId() {
		return FId;
	}

	public void setFId(Long fId) {
		FId = fId;
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSourceType() {
		return fileSourceType;
	}

	public void setFileSourceType(String fileSourceType) {
		this.fileSourceType = fileSourceType;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getFRescCatagory() {
		return FRescCatagory;
	}

	public void setFRescCatagory(Byte fRescCatagory) {
		FRescCatagory = fRescCatagory;
	}
	
	
}

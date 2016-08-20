package com.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="demo_textbook")
public class TextBook {

	private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
	
	private @ManyToOne @JsonIgnore Version version;
	
	private @Column(length=20,name="book_name") String bookName;
	
	@OneToMany(mappedBy="book")
	private List<BookNode> booknodes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Version getVersion() {
		return version;
	}
	public void setVersion(Version version) {
		this.version = version;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public List<BookNode> getBooknodes() {
		return booknodes;
	}
	public void setBooknodes(List<BookNode> booknodes) {
		this.booknodes = booknodes;
	}
	
	
	
}

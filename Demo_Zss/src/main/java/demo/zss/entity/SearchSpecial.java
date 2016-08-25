package demo.zss.entity;

import demo.zss.entity.basic.BookNode;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.TextBook;
import demo.zss.entity.basic.Version;

public class SearchSpecial {
	
	private Long id;
	
	private Byte stage;
	
	private Subject subject;
	
	private Version version;
	
	private TextBook textbook;
	
	private BookNode booknode;
	
	

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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public TextBook getTextbook() {
		return textbook;
	}

	public void setTextbook(TextBook textbook) {
		this.textbook = textbook;
	}

	public BookNode getBooknode() {
		return booknode;
	}

	public void setBooknode(BookNode booknode) {
		this.booknode = booknode;
	}

}

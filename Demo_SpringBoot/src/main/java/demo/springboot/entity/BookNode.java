package demo.springboot.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="zyk_booknode")
public class BookNode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private @ManyToOne(fetch = FetchType.LAZY) TextBook book;
	
	private Byte depth;
	
	private Integer classId;
	
	private Integer chapterId;
	
	private Integer nodeId;
	
	private @JsonIgnore @OneToMany List<BookNode> booknodes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TextBook getBook() {
		return book;
	}

	public void setBook(TextBook book) {
		this.book = book;
	}

	public Byte getDepth() {
		return depth;
	}

	public void setDepth(Byte depth) {
		this.depth = depth;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public List<BookNode> getBooknodes() {
		return booknodes;
	}

	public void setBooknodes(List<BookNode> booknodes) {
		this.booknodes = booknodes;
	}
	
	
}

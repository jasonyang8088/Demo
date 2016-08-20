package demo.zss.entity.basic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="demo_booknode")
public class BookNode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private @ManyToOne @JsonIgnore TextBook book;
	
	private Byte depth;
	
	
	private @OneToMany List<BookNode> booknodes;

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


	public List<BookNode> getBooknodes() {
		return booknodes;
	}

	public void setBooknodes(List<BookNode> booknodes) {
		this.booknodes = booknodes;
	}
	
	
}

package demo.zss.entity.basic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="b_book_node")
public class BookNode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private @ManyToOne @JsonIgnore TextBook book;
	
	private Byte depth;
	
	private String orderNo;
	
	private @ManyToOne BookNode parent;
	
	private @OneToMany List<BookNode> childrens;
	
//	@Transient
//	private boolean isEnd;
//	
//	
//
//	public boolean isEnd() {
//		if(this.childrens.size()==0){
//			return true;
//		}else {
//			return false;
//		}
//		
//	}

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BookNode getParent() {
		return parent;
	}

	public void setParent(BookNode parent) {
		this.parent = parent;
	}

	public List<BookNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<BookNode> childrens) {
		this.childrens = childrens;
	}


	
	
}

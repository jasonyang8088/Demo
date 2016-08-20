package demo.zss.entity.basic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="b_knowledge_point")
public class KnowledgePoint {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToOne
	private Subject subject;
	
	@ManyToOne
	private Version version;
	
	@ManyToOne
	private TextBook textBook;
	
	@ManyToOne
	private BookNode bookNode;
	
	private byte depth;
	
	private String orderNo;
	
	private byte status;   //-1 已失效;0、未审核;1、审核通过
	
	@ManyToOne
	private KnowledgePoint parent;
	
	@OneToMany(mappedBy="parent")
	private List<KnowledgePoint> childrens;
	
	@ManyToOne
	private BasicKnowledgePoint basicKnowledgePoint;

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

	public TextBook getTextBook() {
		return textBook;
	}

	public void setTextBook(TextBook textBook) {
		this.textBook = textBook;
	}

	public BookNode getBookNode() {
		return bookNode;
	}

	public void setBookNode(BookNode bookNode) {
		this.bookNode = bookNode;
	}

	public byte getDepth() {
		return depth;
	}

	public void setDepth(byte depth) {
		this.depth = depth;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public KnowledgePoint getParent() {
		return parent;
	}

	public void setParent(KnowledgePoint parent) {
		this.parent = parent;
	}

	public List<KnowledgePoint> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<KnowledgePoint> childrens) {
		this.childrens = childrens;
	}

	public BasicKnowledgePoint getBasicKnowledgePoint() {
		return basicKnowledgePoint;
	}

	public void setBasicKnowledgePoint(BasicKnowledgePoint basicKnowledgePoint) {
		this.basicKnowledgePoint = basicKnowledgePoint;
	}
}

package demo.zss.entity.basic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "b_document_special_spec")
public class DocumentSpecialSpec {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Subject subject;

	@ManyToOne
	private Version version;

	@ManyToOne
	private TextBook textBook;

	@ManyToOne
	private BookNode bookNode1;

	@ManyToOne
	private BookNode bookNode2;

	@ManyToOne
	private BookNode bookNode3;

	@ManyToOne
	private KnowledgePoint knowledgePoint1;
	@ManyToOne
	private KnowledgePoint knowledgePoint2;
	@ManyToOne
	private KnowledgePoint knowledgePoint3;
	@ManyToOne
	private KnowledgePoint knowledgePoint4;
	@ManyToOne
	private KnowledgePoint knowledgePoint5;

	@ManyToMany(mappedBy = "documentSpecialSpecs")
	private List<Document> documents;

	@Transient
	private BookNode bookNode;

	@Transient
	private KnowledgePoint knowledgePoint;

	@ManyToOne
	private BasicKnowledgePoint basicKnowledgePoint;
	
	@Transient
	private String msg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public KnowledgePoint getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(KnowledgePoint knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public BookNode getBookNode1() {
		return bookNode1;
	}

	public void setBookNode1(BookNode bookNode1) {
		this.bookNode1 = bookNode1;
	}

	public BookNode getBookNode2() {
		return bookNode2;
	}

	public void setBookNode2(BookNode bookNode2) {
		this.bookNode2 = bookNode2;
	}

	public BookNode getBookNode3() {
		return bookNode3;
	}

	public void setBookNode3(BookNode bookNode3) {
		this.bookNode3 = bookNode3;
	}

	public KnowledgePoint getKnowledgePoint1() {
		return knowledgePoint1;
	}

	public void setKnowledgePoint1(KnowledgePoint knowledgePoint1) {
		this.knowledgePoint1 = knowledgePoint1;
	}

	public KnowledgePoint getKnowledgePoint2() {
		return knowledgePoint2;
	}

	public void setKnowledgePoint2(KnowledgePoint knowledgePoint2) {
		this.knowledgePoint2 = knowledgePoint2;
	}

	public KnowledgePoint getKnowledgePoint3() {
		return knowledgePoint3;
	}

	public void setKnowledgePoint3(KnowledgePoint knowledgePoint3) {
		this.knowledgePoint3 = knowledgePoint3;
	}

	public KnowledgePoint getKnowledgePoint4() {
		return knowledgePoint4;
	}

	public void setKnowledgePoint4(KnowledgePoint knowledgePoint4) {
		this.knowledgePoint4 = knowledgePoint4;
	}

	public KnowledgePoint getKnowledgePoint5() {
		return knowledgePoint5;
	}

	public BasicKnowledgePoint getBasicKnowledgePoint() {
		return basicKnowledgePoint;
	}

	public void setBasicKnowledgePoint(BasicKnowledgePoint basicKnowledgePoint) {
		this.basicKnowledgePoint = basicKnowledgePoint;
	}

	public void setKnowledgePoint5(KnowledgePoint knowledgePoint5) {
		this.knowledgePoint5 = knowledgePoint5;
	}

	public String getMsg() {
		StringBuilder sb = new StringBuilder();
		byte stage = this.getSubject().getStage();
		String stagename="";
		if (stage == 2) {
			stagename = "初中";
		} else if (stage == 3) {
			stagename = "高中";
		}
		sb.append("" + stagename);
		if (null != this.getSubject()) {
			sb.append(">" + this.getSubject().getSubjectName());
		}
		if (null != this.getVersion()) {
			sb.append(">" + this.getVersion().getVersionName());
		}
		if(null!=this.getTextBook()){
			sb.append(">"+this.getTextBook().getBookName());
		}
		if(null!=this.getBookNode1()){
			sb.append(">"+this.getBookNode1().getName());
		}
		if(null!=this.getBookNode2()){
			sb.append(">"+this.getBookNode2().getName());
		}
		if(null!=this.getBookNode3()){
			sb.append(">"+this.getBookNode3().getName());
		}
		if(null!=this.getKnowledgePoint1()){
			sb.append(">"+this.getKnowledgePoint1().getName());
		}
		if(null!=this.getKnowledgePoint2()){
			sb.append(">"+this.getKnowledgePoint2().getName());
		}
		if(null!=this.getKnowledgePoint3()){
			sb.append(">"+this.getKnowledgePoint3().getName());
		}
		if(null!=this.getKnowledgePoint4()){
			sb.append(">"+this.getKnowledgePoint4().getName());
		}
		if(null!=this.getKnowledgePoint5()){
			sb.append(">"+this.getKnowledgePoint5().getName());
		}
		return sb.toString();
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

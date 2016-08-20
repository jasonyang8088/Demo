package demo.zss.entity.basic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="b_document_special_spec")
public class DocumentSpecialSpec {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Subject subject;
	
	@ManyToOne 
	private Version version;
	
	@ManyToOne 
	private TextBook textBook;
	
	@ManyToOne 
	private BookNode bookNode;
	
	@ManyToOne 
	private BasicKnowledgePoint basicKnowledgePoint;

	@ManyToMany(mappedBy="documentSpecialSpecs")
	private List<Document> documents;

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

	public BasicKnowledgePoint getBasicKnowledgePoint() {
		return basicKnowledgePoint;
	}

	public void setBasicKnowledgePoint(BasicKnowledgePoint basicKnowledgePoint) {
		this.basicKnowledgePoint = basicKnowledgePoint;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}

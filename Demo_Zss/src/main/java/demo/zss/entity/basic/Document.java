package demo.zss.entity.basic;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import demo.zss.entity.user.User;

@Entity(name="b_document")
public class Document {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToMany
	private List<DocumentSpecialSpec> documentSpecialSpecs;
	
	private String name;
	
	private Integer size;
	
	private Byte status;
	
	private String docSuffix;
	
	private Date createTime;
	
	@ManyToOne
	private User createUser;
	
	private String intro;
	
	private String mongoFileId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<DocumentSpecialSpec> getDocumentSpecialSpecs() {
		return documentSpecialSpecs;
	}

	public void setDocumentSpecialSpecs(List<DocumentSpecialSpec> documentSpecialSpecs) {
		this.documentSpecialSpecs = documentSpecialSpecs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getDocSuffix() {
		return docSuffix;
	}

	public void setDocSuffix(String docSuffix) {
		this.docSuffix = docSuffix;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getMongoFileId() {
		return mongoFileId;
	}

	public void setMongoFileId(String mongoFileId) {
		this.mongoFileId = mongoFileId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
}

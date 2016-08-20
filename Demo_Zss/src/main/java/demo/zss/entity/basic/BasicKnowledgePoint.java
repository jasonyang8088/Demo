package demo.zss.entity.basic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="b_basic_knowledge_point")
public class BasicKnowledgePoint {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;

	@ManyToOne
	private Subject subject;

	private byte depth;

	private String orderNo;
	
	private byte status;   //-1 已失效;0、未审核;1、审核通过
	
	@ManyToOne
	private BasicKnowledgePoint parent;
	
	@OneToMany(mappedBy="parent")
	private List<BasicKnowledgePoint> childrens;

	@OneToMany(mappedBy = "basicKnowledgePoint")
	private List<KnowledgePoint> knowledgePoints;

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

	public BasicKnowledgePoint getParent() {
		return parent;
	}

	public void setParent(BasicKnowledgePoint parent) {
		this.parent = parent;
	}

	public List<BasicKnowledgePoint> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<BasicKnowledgePoint> childrens) {
		this.childrens = childrens;
	}

	public List<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(List<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}
}

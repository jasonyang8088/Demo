package demo.zss.entity.mongo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Entity
public class ZDocument {

	@Id
	private Long id;
	
	private String name;
	
//	@DBRef
	private @ManyToOne ZSubject sub;
	
	private @OneToMany List<ZSubject> subs;

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

	public ZSubject getSub() {
		return sub;
	}

	public void setSub(ZSubject sub) {
		this.sub = sub;
	}

	public List<ZSubject> getSubs() {
		return subs;
	}

	public void setSubs(List<ZSubject> subs) {
		this.subs = subs;
	}
	
	
}

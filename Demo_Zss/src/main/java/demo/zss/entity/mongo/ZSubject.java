package demo.zss.entity.mongo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
public class ZSubject {

	@Id
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy="sub")
	@DBRef
	private List<ZDocument> docs;

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

	public List<ZDocument> getDocs() {
		return docs;
	}

	public void setDocs(List<ZDocument> docs) {
		this.docs = docs;
	}
	
}

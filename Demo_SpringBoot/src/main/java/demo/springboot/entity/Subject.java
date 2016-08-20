package demo.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="zyk_subject")
public class Subject {

	private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
	private Byte stage;
	private @Column(length=6,name="subject_name") String subjectName;
	private @Column(name="channel_id") Integer channelId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getStage() {
		return stage;
	}
	public void setStage(Byte stage) {
		this.stage = stage;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
}

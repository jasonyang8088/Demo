package demo.zss.controller.form;

public class TextBookManagerSearchForm {
	
	private Byte stage;
	
	private Long subjectId;
	
	private Long versionId;
	
	private Integer status;
	

	public Byte getStage() {
		return stage;
	}

	public void setStage(Byte stage) {
		this.stage = stage;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

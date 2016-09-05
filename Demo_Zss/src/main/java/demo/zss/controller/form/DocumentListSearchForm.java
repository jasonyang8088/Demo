package demo.zss.controller.form;

public class DocumentListSearchForm {
	
	private Byte stage;
	
	private Long subjectId;
	
	private Long versionId;
	
	private Long textBookId;
	
	private Byte status;
	

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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Long getTextBookId() {
		return textBookId;
	}

	public void setTextBookId(Long textBookId) {
		this.textBookId = textBookId;
	}
}

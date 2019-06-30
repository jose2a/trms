package com.revature.trms.pojos;

import java.time.LocalDate;
import java.util.Arrays;

public class Attachment {

	private Integer attachmentId;
	
	private String fileName;
	private LocalDate dateSubmitted;
	private boolean isApprovalDoc;
	private byte[] fileContent;
	private AttachmentApprovalType approvalStage;
	
	private Integer eventId;

	public Attachment() {
		super();
	}

	public Attachment(String fileName, LocalDate dateSubmitted, boolean isApprovalDoc, byte[] fileContent,
			AttachmentApprovalType approvalStage, int eventId) {
		super();
		this.fileName = fileName;
		this.dateSubmitted = dateSubmitted;
		this.isApprovalDoc = isApprovalDoc;
		this.fileContent = fileContent;
		this.approvalStage = approvalStage;
		this.eventId = eventId;
	}

	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDate getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(LocalDate dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public boolean isApprovalDoc() {
		return isApprovalDoc;
	}

	public void setApprovalDoc(boolean isApprovalDoc) {
		this.isApprovalDoc = isApprovalDoc;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public AttachmentApprovalType getApprovalStage() {
		return approvalStage;
	}

	public void setApprovalStage(AttachmentApprovalType approvalStage) {
		this.approvalStage = approvalStage;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStage == null) ? 0 : approvalStage.hashCode());
		result = prime * result + attachmentId;
		result = prime * result + ((dateSubmitted == null) ? 0 : dateSubmitted.hashCode());
		result = prime * result + eventId;
		result = prime * result + Arrays.hashCode(fileContent);
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + (isApprovalDoc ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		if (approvalStage != other.approvalStage)
			return false;
		if (attachmentId != other.attachmentId)
			return false;
		if (dateSubmitted == null) {
			if (other.dateSubmitted != null)
				return false;
		} else if (!dateSubmitted.equals(other.dateSubmitted))
			return false;
		if (eventId != other.eventId)
			return false;
		if (!Arrays.equals(fileContent, other.fileContent))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (isApprovalDoc != other.isApprovalDoc)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attachment [attachmentId=" + attachmentId + ", fileName=" + fileName + ", dateSubmitted="
				+ dateSubmitted + ", isApprovalDoc=" + isApprovalDoc + ", fileContent=" + fileContent
				+ ", approvalStage=" + approvalStage + ", eventId=" + eventId + "]";
	}

}

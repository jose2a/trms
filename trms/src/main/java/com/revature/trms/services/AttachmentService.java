package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.Attachment;

public interface AttachmentService {

	public boolean addAttachment(Attachment attachment);

	public boolean updateAttachment(Attachment attachment);

	public boolean deleteAttachment(int attachmentId);

	public Attachment getAttachmentById(int attachmentId);

	public List<Attachment> getAttachmentsByEventId(int eventId);
}

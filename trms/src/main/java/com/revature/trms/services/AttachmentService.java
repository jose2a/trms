package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.Attachment;

public interface AttachmentService {

	public boolean addAttachment(Attachment attachment);

	public boolean deleteAttachment(Integer attachmentId);

	public Attachment getAttachmentById(Integer attachmentId);

	public List<Attachment> getAttachmentsByEventId(Integer eventId);
}

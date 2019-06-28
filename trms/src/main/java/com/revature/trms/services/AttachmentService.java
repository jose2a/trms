package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;

public interface AttachmentService {

	public boolean addAttachment(Attachment attachment) throws PojoValidationException;

	public Attachment getAttachmentById(Integer attachmentId) throws PojoValidationException;

	public List<Attachment> getAttachmentsByEventId(Integer eventId);
}

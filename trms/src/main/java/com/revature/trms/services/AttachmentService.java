package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;

public interface AttachmentService {

	public boolean addAttachment(Attachment attachment) throws PojoValidationException, IllegalParameterException;

	public Attachment getAttachmentById(Integer attachmentId) throws IllegalParameterException;

	public List<Attachment> getAttachmentsByEventId(Integer eventId) throws IllegalParameterException;
}

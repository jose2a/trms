package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.Attachment;

public interface AttachmentDAO {

	public boolean addAttachment(Attachment attachment);

	public boolean deleteAttachment(int attachmentId);

	public Attachment getAttachmentById(int attachmentId);

	public List<Attachment> getAttachmentsByEventId(int eventId);
}

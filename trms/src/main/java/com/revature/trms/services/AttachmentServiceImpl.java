package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.AttachmentDAO;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class AttachmentServiceImpl extends BaseService implements AttachmentService {

	private AttachmentDAO attachmentDao;

	public AttachmentServiceImpl() {
		attachmentDao = DAOUtilities.getAttachmentDAO();
	}

	@Override
	public boolean addAttachment(Attachment attachment) throws PojoValidationException {
		validateAttachment(attachment);
		checkValidationResults();

		if (attachment.getEventId() == null) {
			throw new IllegalArgumentException("Employee id should not be empty.");
		}

		LogUtilities.trace("No validation errors - addAttachment.");

		return attachmentDao.addAttachment(attachment);
	}

	@Override
	public Attachment getAttachmentById(Integer attachmentId) throws PojoValidationException {
		validateOnlyAttachmentId(attachmentId);
		checkValidationResults();

		LogUtilities.trace("No validation errors - getAttachmentById");

		return attachmentDao.getAttachmentById(attachmentId);
	}

	@Override
	public List<Attachment> getAttachmentsByEventId(Integer eventId) {
		if (eventId == null) {
			throw new IllegalArgumentException("Event id should not be empty.");
		}

		LogUtilities.trace("No validation errors - getAttachmentsByEventId");

		return attachmentDao.getAttachmentsByEventId(eventId);
	}

}

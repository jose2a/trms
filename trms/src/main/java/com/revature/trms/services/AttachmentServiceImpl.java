package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.AttachmentDAO;
import com.revature.trms.exceptions.IllegalParameterException;
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
	public boolean addAttachment(Attachment attachment) throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("addAttachment");

		pojoValException = PojoValidationException.getInstance();
		validateAttachment(attachment, pojoValException);
		checkValidationResults(pojoValException);

		if (attachment.getEventId() == null) {
			throw new IllegalParameterException("addAttachment - EventId should not be empty.");
		}

		return attachmentDao.addAttachment(attachment);
	}

	@Override
	public Attachment getAttachmentById(Integer attachmentId) throws IllegalParameterException {
		LogUtilities.trace("getAttachmentById");

		if (attachmentId == null) {
			throw new IllegalParameterException("getAttachmentById - AttachmentId should not be empty.");
		}

		return attachmentDao.getAttachmentById(attachmentId);
	}

	@Override
	public List<Attachment> getAttachmentsByEventId(Integer eventId) throws IllegalParameterException {
		LogUtilities.trace("getAttachmentsByEventId");

		if (eventId == null) {
			throw new IllegalParameterException("getAttachmentsByEventId - EventId should not be empty.");
		}

		return attachmentDao.getAttachmentsByEventId(eventId);
	}

}

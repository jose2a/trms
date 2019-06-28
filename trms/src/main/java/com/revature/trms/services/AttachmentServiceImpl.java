package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.AttachmentDAO;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.utilities.DAOUtilities;

public class AttachmentServiceImpl extends BaseService implements AttachmentService {
	
	private AttachmentDAO attachmentDao;

	public AttachmentServiceImpl() {
		super();
		
		attachmentDao = DAOUtilities.getAttachmentDAO();
	}

	@Override
	public boolean addAttachment(Attachment attachment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAttachment(Integer attachmentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Attachment getAttachmentById(Integer attachmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attachment> getAttachmentsByEventId(Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

}

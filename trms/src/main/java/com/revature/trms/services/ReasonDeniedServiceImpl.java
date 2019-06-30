package com.revature.trms.services;

import com.revature.trms.daos.ReasonDeniedDAO;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class ReasonDeniedServiceImpl extends BaseService implements ReasonDeniedService {
	
	private ReasonDeniedDAO reasonDeniedDao;
	
	public ReasonDeniedServiceImpl() {
		reasonDeniedDao = DAOUtilities.getReasonDeniedDAO();
	}

	@Override
	public boolean addReasonDenied(ReasonDenied reasonDenied) throws PreexistingRecordException, PojoValidationException {
		LogUtilities.trace("addGradingFormat");
		
		validateReasonDenied(reasonDenied);
		checkValidationResults();
		
		if (reasonDenied.getEventId() == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}
		
		ReasonDenied reasonD = reasonDeniedDao.getReasonDeniedByEventId(reasonDenied.getEventId());
		
		if (reasonD != null) {
			throw new PreexistingRecordException("You previously enter a reson why this project was denied.");
		}

		LogUtilities.trace("No validation errors - addReasonDenied");

		return reasonDeniedDao.addReasonDenied(reasonDenied);
	}

	

	@Override
	public boolean updateReasonDenied(ReasonDenied reasonDenied) throws PojoValidationException {
		LogUtilities.trace("addGradingFormat");
		
		validateReasonDenied(reasonDenied);
		checkValidationResults();
		
		if (reasonDenied.getEventId() == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}

		LogUtilities.trace("No validation errors - updateReasonDenied");

		return reasonDeniedDao.updateReasonDenied(reasonDenied);
	}

	@Override
	public ReasonDenied getReasonDeniedByEventId(Integer eventId) {
		LogUtilities.trace("addGradingFormat");
		
		if (eventId == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}
		
		return reasonDeniedDao.getReasonDeniedByEventId(eventId);
	}

}

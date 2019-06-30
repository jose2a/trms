package com.revature.trms.services;

import com.revature.trms.daos.ReasonDeniedDAO;
import com.revature.trms.exceptions.IllegalParameterException;
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
	public boolean addReasonDenied(ReasonDenied reasonDenied)
			throws PreexistingRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("addGradingFormat");

		pojoValException = PojoValidationException.getInstance();
		validateReasonDenied(reasonDenied, pojoValException);
		checkValidationResults(pojoValException); // check validation results

		if (reasonDenied.getEventId() == null) {
			throw new IllegalParameterException("addReasonDenied - eventId should not be empty.");
		}

		ReasonDenied reasonD = reasonDeniedDao.getReasonDeniedByEventId(reasonDenied.getEventId());

		if (reasonD != null) {
			throw new PreexistingRecordException("You previously enter a reson why this project was denied.");
		}

		return reasonDeniedDao.addReasonDenied(reasonDenied);
	}

	@Override
	public boolean updateReasonDenied(ReasonDenied reasonDenied) throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("updateReasonDenied");

		pojoValException = PojoValidationException.getInstance();
		validateReasonDenied(reasonDenied, pojoValException);
		checkValidationResults(pojoValException); // check validation results

		if (reasonDenied.getEventId() == null) {
			throw new IllegalParameterException("updateReasonDenied - eventId should not be empty");
		}

		return reasonDeniedDao.updateReasonDenied(reasonDenied);
	}

	@Override
	public ReasonDenied getReasonDeniedByEventId(Integer eventId) throws IllegalParameterException {
		LogUtilities.trace("getReasonDeniedByEventId");

		if (eventId == null) {
			throw new IllegalParameterException("getReasonDeniedByEventId - eventId should not be empty");
		}

		return reasonDeniedDao.getReasonDeniedByEventId(eventId);
	}

}

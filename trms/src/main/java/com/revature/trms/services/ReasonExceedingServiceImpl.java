package com.revature.trms.services;

import com.revature.trms.daos.ReasonExceedingDAO;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.ReasonExceeding;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class ReasonExceedingServiceImpl extends BaseService implements ReasonExceedingService {

	private ReasonExceedingDAO reasonExceedingDao;

	public ReasonExceedingServiceImpl() {
		reasonExceedingDao = DAOUtilities.getReasonExceedingDAO();
	}

	@Override
	public boolean addReasonExceeding(ReasonExceeding reasonExceeding)
			throws PojoValidationException, PreexistingRecordException {
		validateReasonExceeding(reasonExceeding);
		checkValidationResults();

		if (reasonExceeding.getEventId() == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}

		ReasonExceeding reasonE = reasonExceedingDao.getReasonExceedingByEventId(reasonExceeding.getEventId());

		if (reasonE != null) {
			throw new PreexistingRecordException(
					"You previously enter a reson why this project was exceeding the amount available.");
		}

		LogUtilities.trace("No validation errors - addReasonExceeding");

		return reasonExceedingDao.addReasonExceeding(reasonExceeding);
	}

	@Override
	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding) throws PojoValidationException {
		validateReasonExceeding(reasonExceeding);
		checkValidationResults();

		if (reasonExceeding.getEventId() == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}

		LogUtilities.trace("No validation errors - updateReasonExceeding");

		return reasonExceedingDao.updateReasonExceeding(reasonExceeding);
	}

	@Override
	public ReasonExceeding getReasonExceedingByEventId(Integer eventId) {
		if (eventId == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}

		return reasonExceedingDao.getReasonExceedingByEventId(eventId);
	}

}

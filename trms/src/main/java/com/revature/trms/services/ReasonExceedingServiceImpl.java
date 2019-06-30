package com.revature.trms.services;

import com.revature.trms.daos.ReasonExceedingDAO;
import com.revature.trms.exceptions.IllegalParameterException;
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
			throws PojoValidationException, PreexistingRecordException, IllegalParameterException {
		LogUtilities.trace("addReasonExceeding");

		pojoValException = PojoValidationException.getInstance();
		validateReasonExceeding(reasonExceeding, pojoValException);
		checkValidationResults(pojoValException); // check validation results

		if (reasonExceeding.getEventId() == null) {
			throw new IllegalParameterException("addReasonExceeding - eventId should not be empty");
		}

		ReasonExceeding reasonE = reasonExceedingDao.getReasonExceedingByEventId(reasonExceeding.getEventId());

		if (reasonE != null) {
			throw new PreexistingRecordException(
					"You previously enter a reson why this project was exceeding the amount available.");
		}

		return reasonExceedingDao.addReasonExceeding(reasonExceeding);
	}

	@Override
	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding)
			throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("updateReasonExceeding");

		pojoValException = PojoValidationException.getInstance();
		validateReasonExceeding(reasonExceeding, pojoValException);
		checkValidationResults(pojoValException); // check validation results

		if (reasonExceeding.getEventId() == null) {
			throw new IllegalParameterException("updateReasonExceeding - reasonExceeding should not be empty");
		}

		LogUtilities.trace("No validation errors - updateReasonExceeding");

		return reasonExceedingDao.updateReasonExceeding(reasonExceeding);
	}

	@Override
	public ReasonExceeding getReasonExceedingByEventId(Integer eventId) throws IllegalParameterException {
		LogUtilities.trace("addGradingFormat");

		if (eventId == null) {
			throw new IllegalParameterException("getReasonExceedingByEventId - eventId should not be empty");
		}

		return reasonExceedingDao.getReasonExceedingByEventId(eventId);
	}

}

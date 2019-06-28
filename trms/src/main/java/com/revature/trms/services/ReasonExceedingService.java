package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.ReasonExceeding;

public interface ReasonExceedingService {

	public boolean addReasonExceeding(ReasonExceeding reasonExceeding)
			throws PojoValidationException, PreexistingRecordException;

	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding) throws PojoValidationException;

	public ReasonExceeding getReasonExceedingByEventId(Integer eventId);
	
}

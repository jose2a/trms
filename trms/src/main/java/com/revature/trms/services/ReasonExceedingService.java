package com.revature.trms.services;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.ReasonExceeding;

public interface ReasonExceedingService {

	public boolean addReasonExceeding(ReasonExceeding reasonExceeding)
			throws PojoValidationException, PreexistingRecordException, IllegalParameterException;

	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding)
			throws PojoValidationException, IllegalParameterException;

	public ReasonExceeding getReasonExceedingByEventId(Integer eventId) throws IllegalParameterException;
	
}

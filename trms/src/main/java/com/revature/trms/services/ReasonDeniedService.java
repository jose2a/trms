package com.revature.trms.services;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.ReasonDenied;

public interface ReasonDeniedService {

	public boolean addReasonDenied(ReasonDenied reasonDenied)
			throws PreexistingRecordException, PojoValidationException, IllegalParameterException;

	public boolean updateReasonDenied(ReasonDenied reasonDenied)
			throws PojoValidationException, IllegalParameterException;

	public ReasonDenied getReasonDeniedByEventId(Integer eventId) throws IllegalParameterException;
}

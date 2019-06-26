package com.revature.trms.services;

import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.pojos.ReasonExceeding;

public interface ReasonExceedingService {

	public boolean addReasonExceeding(ReasonExceeding reasonExceeding);

	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding);

	public ReasonDenied getReasonExceedingByEventId(Integer eventId);
	
}

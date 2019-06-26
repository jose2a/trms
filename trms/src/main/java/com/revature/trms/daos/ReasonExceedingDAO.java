package com.revature.trms.daos;

import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.pojos.ReasonExceeding;

public interface ReasonExceedingDAO {
	public boolean addReasonExceeding(ReasonExceeding reasonExceeding);

	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding);

	public ReasonDenied getReasonExceedingByEventId(Integer eventId);
}

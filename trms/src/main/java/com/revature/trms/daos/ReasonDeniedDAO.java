package com.revature.trms.daos;

import com.revature.trms.pojos.ReasonDenied;

public interface ReasonDeniedDAO {
	public boolean addReasonDenied(ReasonDenied reasonDenied);

	public boolean updateReasonDenied(ReasonDenied reasonDenied);

	public ReasonDenied getReasonDeniedByEventId(Integer eventId);
}

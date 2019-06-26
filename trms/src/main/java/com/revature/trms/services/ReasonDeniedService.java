package com.revature.trms.services;

import com.revature.trms.pojos.ReasonDenied;

public interface ReasonDeniedService {

	public boolean addReasonDenied(ReasonDenied reasonDenied);

	public boolean updateReasonDenied(ReasonDenied reasonDenied);

	public ReasonDenied getReasonDeniedByEventId(Integer eventId);
}

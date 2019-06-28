package com.revature.trms.services;

import com.revature.trms.daos.ReasonExceedingDAO;
import com.revature.trms.pojos.ReasonExceeding;
import com.revature.trms.utilities.DAOUtilities;

public class ReasonExceedingServiceImpl extends BaseService implements ReasonExceedingService {
	
	private ReasonExceedingDAO reasonExceedingDao;

	public ReasonExceedingServiceImpl() {
		reasonExceedingDao = DAOUtilities.getReasonExceedingDAO();
	}
	
	@Override
	public boolean addReasonExceeding(ReasonExceeding reasonExceeding) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReasonExceeding(ReasonExceeding reasonExceeding) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReasonExceeding getReasonExceedingByEventId(Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

}

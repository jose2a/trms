package com.revature.trms.services;

import com.revature.trms.daos.ReasonDeniedDAO;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.utilities.DAOUtilities;

public class ReasonDeniedServiceImpl extends BaseService implements ReasonDeniedService {
	
	private ReasonDeniedDAO reasonDeniedDao;
	
	public ReasonDeniedServiceImpl() {
		reasonDeniedDao = DAOUtilities.getReasonDeniedDAO();
	}

	@Override
	public boolean addReasonDenied(ReasonDenied reasonDenied) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReasonDenied(ReasonDenied reasonDenied) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReasonDenied getReasonDeniedByEventId(Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

}

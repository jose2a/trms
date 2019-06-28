package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.InformationRequiredDAO;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.utilities.DAOUtilities;

public class InformationRequiredServiceImpl extends BaseService implements InformationRequiredService {
	
	private InformationRequiredDAO informationRequiredDao;
	
	public InformationRequiredServiceImpl() {
		informationRequiredDao = DAOUtilities.getInformationRequireDAO();
	}

	@Override
	public boolean addInformationRequired(InformationRequired informationRequired) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateInformationRequired(InformationRequired informationRequired) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<InformationRequired> getInformationRequiredByEmployeeId(Integer employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InformationRequired getInformationRequiredByEmployeeIdAndEventId(Integer employeeId, Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

}

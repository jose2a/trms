package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.InformationRequiredDAO;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class InformationRequiredServiceImpl extends BaseService implements InformationRequiredService {
	
	private InformationRequiredDAO informationRequiredDao;
	
	public InformationRequiredServiceImpl() {
		informationRequiredDao = DAOUtilities.getInformationRequireDAO();
	}

	@Override
	public boolean addInformationRequired(InformationRequired informationRequired) throws PojoValidationException, PreexistingRecordException {
		validateInformationRequired(informationRequired);
		checkValidationResults();
		
		if (informationRequired.getEventId() == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}
		
		if (informationRequired.getRequiredBy() == null) {
			throw new IllegalArgumentException("RequiredBy should not be empty.");
		}
		
		InformationRequired infoReq = informationRequiredDao.getInformationRequiredByEmployeeIdAndEventId(informationRequired.getEmployeeId(), informationRequired.getEventId());
		
		if (infoReq != null) {
			throw new PreexistingRecordException("You previously requested additonal information from this employee");
		}

		LogUtilities.trace("No validation errors - addGradingFormat");

		return informationRequiredDao.addInformationRequired(informationRequired);
	}

	@Override
	public boolean updateInformationRequired(InformationRequired informationRequired) throws PojoValidationException {
		validateInformationRequired(informationRequired);
		checkValidationResults();
		
		if (informationRequired.getEventId() == null) {
			throw new IllegalArgumentException("EventId should not be empty.");
		}
		
		if (informationRequired.getRequiredBy() == null) {
			throw new IllegalArgumentException("RequiredBy should not be empty.");
		}

		LogUtilities.trace("No validation errors - updateInformationRequired");

		return informationRequiredDao.addInformationRequired(informationRequired);
	}

	@Override
	public List<InformationRequired> getInformationRequiredByEmployeeId(Integer employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("EmployeeId should be provided.");
		}
		
		return informationRequiredDao.getInformationRequiredByEmployeeId(employeeId);
	}

}

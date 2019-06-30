package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.InformationRequiredDAO;
import com.revature.trms.exceptions.IllegalParameterException;
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
	public boolean addInformationRequired(InformationRequired informationRequired)
			throws PojoValidationException, PreexistingRecordException, IllegalParameterException {
		LogUtilities.trace("addInformationRequired");

		pojoValException = PojoValidationException.getInstance();
		validateInformationRequired(informationRequired, pojoValException);
		checkValidationResults(pojoValException);

		validateParams(informationRequired);

		InformationRequired infoReq = informationRequiredDao.getInformationRequiredByEmployeeIdAndEventId(
				informationRequired.getEmployeeId(), informationRequired.getEventId());

		if (infoReq != null) {
			throw new PreexistingRecordException("You previously requested additonal information from this employee");
		}

		return informationRequiredDao.addInformationRequired(informationRequired);
	}

	private void validateParams(InformationRequired informationRequired) throws IllegalParameterException {
		if (informationRequired.getEventId() == null) {
			throw new IllegalParameterException("EventId should not be empty.");
		}

		if (informationRequired.getRequiredBy() == null) {
			throw new IllegalParameterException("RequiredBy should not be empty.");
		}
	}

	@Override
	public boolean updateInformationRequired(InformationRequired informationRequired)
			throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("updateInformationRequired");

		pojoValException = PojoValidationException.getInstance();
		validateInformationRequired(informationRequired, pojoValException);
		checkValidationResults(pojoValException);

		validateParams(informationRequired);

		return informationRequiredDao.addInformationRequired(informationRequired);
	}

	@Override
	public List<InformationRequired> getInformationRequiredByEmployeeId(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("getInformationRequiredByEmployeeId");

		if (employeeId == null) {
			throw new IllegalParameterException("EmployeeId should be provided.");
		}

		return informationRequiredDao.getInformationRequiredByEmployeeId(employeeId);
	}

}

package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.InformationRequired;

public interface InformationRequiredService {

	public boolean addInformationRequired(InformationRequired informationRequired)
			throws PojoValidationException, PreexistingRecordException;

	public boolean updateInformationRequired(InformationRequired informationRequired) throws PojoValidationException;
	
	public List<InformationRequired> getInformationRequiredByEmployeeId(Integer employeeId);
}

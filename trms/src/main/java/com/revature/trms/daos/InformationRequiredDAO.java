package com.revature.trms.daos;

import com.revature.trms.pojos.InformationRequired;

public interface InformationRequiredDAO {

	public boolean addInformationRequired(InformationRequired informationRequired);

	public boolean updateInformationRequired(InformationRequired informationRequired);
	
	public InformationRequired getInformationRequiredByEmployeeId(Integer employeeId);

	public InformationRequired getInformationRequiredByEmployeeIdAndEventId(Integer employeeId, Integer eventId);
}

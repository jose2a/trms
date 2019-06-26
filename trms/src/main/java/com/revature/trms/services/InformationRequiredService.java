package com.revature.trms.services;

import com.revature.trms.pojos.InformationRequired;

public interface InformationRequiredService {

	public boolean addInformationRequired(InformationRequired informationRequired);

	public boolean updateInformationRequired(InformationRequired informationRequired);

	public InformationRequired getInformationRequiredByEmployeeIdAndEventId(Integer employeeId, Integer eventId);
}

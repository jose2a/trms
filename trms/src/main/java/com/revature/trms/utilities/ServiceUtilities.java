package com.revature.trms.utilities;

import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeServiceImpl;

public class ServiceUtilities {
	
	public static EmployeeService getEmployeeService() {
		return new EmployeeServiceImpl();
	}

}

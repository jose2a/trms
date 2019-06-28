package com.revature.trms.utilities;

import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeServiceImpl;
import com.revature.trms.services.EmployeeTypeService;
import com.revature.trms.services.EmployeeTypeServiceImpl;

public class ServiceUtilities {
	
	/**
	 * Creating a new instance of EmployeeService.
	 * @return EmployeeService
	 */
	public static EmployeeService getEmployeeService() {
		return new EmployeeServiceImpl();
	}
	
	/**
	 * Creating a new instance of EmployeeTypeService.
	 * @return EmployeeTypeService
	 */
	public static EmployeeTypeService geEmployeeTypeService() {
		return new EmployeeTypeServiceImpl();
	}

}

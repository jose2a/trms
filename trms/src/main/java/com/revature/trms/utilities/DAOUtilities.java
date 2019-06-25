package com.revature.trms.utilities;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.daos.EmployeeDAOImpl;
import com.revature.trms.daos.EmployeeTypeDAO;
import com.revature.trms.daos.EmployeeTypeDAOImpl;

public class DAOUtilities {

	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAOImpl();
	}
	
	public static EmployeeTypeDAO getEmployeeTypeDAO() {
		return new EmployeeTypeDAOImpl();
	}

}

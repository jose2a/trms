package com.revature.trms.utilities;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.daos.EmployeeDAOImpl;

public class DAOUtilities {

	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAOImpl();
	}

}

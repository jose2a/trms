package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;

public abstract class BaseService {

	protected PojoValidationException pojoValExcep;

	protected void validateEmployee(Employee employee) {
		pojoValExcep = new PojoValidationException();

		validateEmployeeUsername(employee);

		if (employee.getFirstName() == null && employee.getFirstName().isEmpty()) {
			pojoValExcep.addError("First Name should not be empty.");
		}

		if (employee.getLastName() == null && employee.getLastName().isEmpty()) {
			pojoValExcep.addError("Last Name should not be empty.");
		}

		if (employee.getSupervisorId() == null) {
			pojoValExcep.addError("Supervisor should be selected.");
		}

	}

	protected void validateEmployeeUsername(Employee employee) {
		if (employee.getUsername() == null || employee.getUsername().isEmpty()) {
			pojoValExcep.addError("Username should not be empty.");
		}
	}

}

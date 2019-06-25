package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;

public abstract class BaseService {

	protected PojoValidationException pojoValidationException;

	protected void validateEmployee(Employee employee) {
		pojoValidationException = new PojoValidationException();

		validateEmployeeUsername(employee);

		if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
			pojoValidationException.addError("First Name should not be empty.");
		}

		if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
			pojoValidationException.addError("Last Name should not be empty.");
		}

		if (employee.getSupervisorId() == null) {
			pojoValidationException.addError("Supervisor should be selected.");
		}

	}

	protected void validateEmployeeUsername(Employee employee) {
		if (employee.getUsername() == null || employee.getUsername().isEmpty()) {
			pojoValidationException.addError("Username should not be empty.");
		}
	}

}

package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;

public abstract class BaseService {

	protected PojoValidationException pojoValidationException;

	private void initPojoValidationException() {
		if (pojoValidationException == null) {
			pojoValidationException = new PojoValidationException();
		}
	}

	protected void checkValidationResults() throws PojoValidationException {
		if (pojoValidationException.getErrors().size() > 0) {
			throw pojoValidationException;
		}
	}

	protected void validateEmployee(Employee employee) {
		initPojoValidationException();

		validateEmployeeUsername(employee.getUsername());

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

	protected void validateEmployeeUsername(String username) {
		initPojoValidationException();

		if (username == null || username.isEmpty()) {
			pojoValidationException.addError("Username should not be empty.");
		}
	}

	protected void validateEmployeePassword(String password) {
		initPojoValidationException();

		if (password == null || password.isEmpty()) {
			pojoValidationException.addError("Password should not be empty.");
		}
	}

}

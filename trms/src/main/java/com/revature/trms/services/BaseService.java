package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;

public abstract class BaseService {

	// Saves validation errors in a list inside the exception
	// so that we can throw after validating everything we need
	protected PojoValidationException pojoValidationException = new PojoValidationException();

	// Clean validation errors from the exception
	private void cleanValidationResults() {
		pojoValidationException.getErrors().clear();
	}

	// Check if we have validation errors, if we have them we throw them
	protected void checkValidationResults() throws PojoValidationException {
		if (pojoValidationException.getErrors().size() > 0) {
			throw pojoValidationException;
		}
	}

	// Validating employee
	protected void validateEmployee(Employee employee) {
		cleanValidationResults();

		validateEmployeeUsername(employee.getUsername());

		if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
			pojoValidationException.addError("First Name should not be empty.");
		}

		if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
			pojoValidationException.addError("Last Name should not be empty.");
		}

		validateSupervisorId(employee.getSupervisorId());

	}

	// Validating supervisor id
	protected void validateSupervisorId(Integer supervisorId) {
		if (supervisorId == null) {
			pojoValidationException.addError("Supervisor should be selected.");
		}
	}

	protected void validateSupervisorId(Integer supervisorId, boolean clean) {
		cleanValidationResults();
		validateSupervisorId(supervisorId);
	}

	// Validating employee username
	protected void validateEmployeeUsername(String username) {

		if (username == null || username.isEmpty()) {
			pojoValidationException.addError("Username should not be empty.");
		}
	}

	protected void validateEmployeeUsername(String username, boolean clean) {
		cleanValidationResults();
		validateEmployeeUsername(username);
	}

	// Validating employee password
	protected void validateEmployeePassword(String password) {

		if (password == null || password.isEmpty()) {
			pojoValidationException.addError("Password should not be empty.");
		}
	}

	protected void validateEmployeePassword(String password, boolean clean) {
		cleanValidationResults();
		validateEmployeePassword(password);
	}

}

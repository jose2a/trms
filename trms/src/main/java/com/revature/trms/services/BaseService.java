package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;

public abstract class BaseService {

	protected PojoValidationException pojoValidationException = new PojoValidationException();

//	private void initPojoValidationException(boolean clean) {
//		if (pojoValidationException == null) {
//			pojoValidationException = new PojoValidationException();
//		}
//		
//		if (clean) {
//			pojoValidationException.getErrors().clear();
//		}
//	}

	private void cleanValidationResults() {
		pojoValidationException.getErrors().clear();
	}

	protected void checkValidationResults() throws PojoValidationException {
		if (pojoValidationException.getErrors().size() > 0) {
			throw pojoValidationException;
		}
	}

	protected void validateEmployee(Employee employee) {
		cleanValidationResults();

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

		if (username == null || username.isEmpty()) {
			pojoValidationException.addError("Username should not be empty.");
		}
	}

	protected void validateEmployeeUsername(String username, boolean clean) {
		cleanValidationResults();
		validateEmployeeUsername(username);
	}

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

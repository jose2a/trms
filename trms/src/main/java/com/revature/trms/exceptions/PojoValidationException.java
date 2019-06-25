package com.revature.trms.exceptions;

import java.util.ArrayList;
import java.util.List;

public class PojoValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// List of errors when validating the pojo,
	// this way all errors are stored until we are ready to throw them
	private List<String> errors;

	public PojoValidationException() {
		if (errors == null) {
			errors = new ArrayList<>();
		}
	}

	public void addError(String error) {
		errors.add(error);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}

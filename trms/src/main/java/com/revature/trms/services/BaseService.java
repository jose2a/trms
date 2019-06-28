package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.pojos.ReasonExceeding;

public abstract class BaseService {

	/**
	 * Saves validation errors in a list inside the exception so that we can throw
	 * after validating everything we need
	 */
	protected PojoValidationException pojoValidationException = new PojoValidationException();

	/**
	 * Cleaning validation errors from the exception.
	 */
	private void cleanValidationResults() {
		pojoValidationException.getErrors().clear();
	}

	/**
	 * Checking if we have validation errors, if we have them we throw them.
	 * 
	 * @throws PojoValidationException
	 */
	protected void checkValidationResults() throws PojoValidationException {
		if (pojoValidationException.getErrors().size() > 0) {
			throw pojoValidationException;
		}
	}

	/**
	 * Validating employee.
	 * 
	 * @param employee The employee.
	 * @throws PojoValidationException
	 */
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

	/**
	 * Validating supervisorId.
	 * 
	 * @param supervisorId The id
	 */
	private void validateSupervisorId(Integer supervisorId) {
		if (supervisorId == null) {
			pojoValidationException.addError("Supervisor should be selected.");
		}
	}

	/**
	 * Validating only the supervisorId.
	 * 
	 * @param supervisorId The Id
	 */
	protected void validateOnlySupervisorId(Integer supervisorId) {
		cleanValidationResults();

		validateSupervisorId(supervisorId);
	}

	/**
	 * Validating employee username.
	 * 
	 * @param username The username
	 */
	private void validateEmployeeUsername(String username) {
		if (username == null || username.isEmpty()) {
			pojoValidationException.addError("Username should not be empty.");
		}
	}

	/**
	 * Validating only employee username.
	 * 
	 * @param username The username
	 */
	protected void validateOnlyEmployeeUsername(String username) {
		cleanValidationResults();

		validateEmployeeUsername(username);
	}

	/**
	 * Validating employee password.
	 * 
	 * @param password The password
	 */
	private void validateEmployeePassword(String password) {
		if (password == null || password.isEmpty()) {
			pojoValidationException.addError("Password should not be empty.");
		}
	}

	/**
	 * Validating only employee password.
	 * 
	 * @param password The password
	 */
	protected void validateOnlyEmployeePassword(String password) {
		cleanValidationResults();

		validateEmployeePassword(password);
	}

	/**
	 * Validates an attachment properties.
	 * 
	 * @param attachment The attachment
	 */
	protected void validateAttachment(Attachment attachment) {
		cleanValidationResults();

		if (attachment.getFileName().equals("") || attachment.getFileName().isEmpty()) {
			pojoValidationException.addError("File name should not be empty.");
		}

		if (attachment.getDateSubmitted() == null) {
			pojoValidationException.addError("Date submitted should not be empty.");
		}

		if (attachment.isApprovalDoc()) {
			if (attachment.getApprovalStage() == null) {
				pojoValidationException.addError("You should select who approve this document.");
			}
		}

		if (attachment.getFileContent().equals(null)) {
			pojoValidationException.addError("You should upload a document.");
		}

	}

	/**
	 * Validating attachmentId.
	 * 
	 * @param attachmentId The id
	 */
	private void validateAttachmentId(Integer attachmentId) {
		if (attachmentId == null) {
			pojoValidationException.addError("Attachment id should not be empty.");
		}
	}

	/**
	 * Validating only attachmentId.
	 * 
	 * @param attachmentId The id
	 */
	protected void validateOnlyAttachmentId(Integer attachmentId) {
		cleanValidationResults();

		validateAttachmentId(attachmentId);
	}

	/**
	 * Validating grade format properties.
	 * 
	 * @param gradingFormat The grading format
	 */
	protected void validateGradingFormat(GradingFormat gradingFormat) {
		cleanValidationResults();

		if (gradingFormat.getFromRange().equals("") || gradingFormat.getFromRange().isEmpty()) {
			pojoValidationException.addError("From should not be empty.");
		}

		if (gradingFormat.getToRange().equals("") || gradingFormat.getToRange().isEmpty()) {
			pojoValidationException.addError("To should not be empty.");
		}
	}

	/**
	 * Validating information required properties.
	 * 
	 * @param informationRequired The information required
	 */
	protected void validateInformationRequired(InformationRequired informationRequired) {
		cleanValidationResults();

		if (informationRequired.getInformation().equals("") || informationRequired.getInformation().isEmpty()) {
			pojoValidationException.addError("Please provide the information.");
		}
	}

	protected void validateReasonDenied(ReasonDenied reasonDenied) {
		cleanValidationResults();

		if (reasonDenied.getReason().equals("") || reasonDenied.getReason().isEmpty()) {
			pojoValidationException.addError("Please provide the reason why this request was denied.");
		}
	}
	
	protected void validateReasonExceeding(ReasonExceeding reasonExceeding) {
		cleanValidationResults();

		if (reasonExceeding.getReason().equals("") || reasonExceeding.getReason().isEmpty()) {
			pojoValidationException.addError("Please provide the reason why the amount for this request was exceeding.");
		}
	}

}

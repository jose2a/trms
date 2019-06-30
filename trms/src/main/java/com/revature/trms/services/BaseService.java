package com.revature.trms.services;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.pojos.ReasonExceeding;
import com.revature.trms.utilities.LogUtilities;

public abstract class BaseService {

	/**
	 * Saves validation errors in a list inside the exception so that we can throw
	 * after validating everything we need
	 */
	protected PojoValidationException pojoValException;

	/**
	 * Cleaning validation errors from the exception.
	 */
	private void cleanValidationResults() {
		pojoValException.getErrors().clear();
	}

	/**
	 * Checking if we have validation errors, if we have them we throw them.
	 * 
	 * @throws PojoValidationException
	 */
	protected void checkValidationResults() throws PojoValidationException {
		if (pojoValException != null && pojoValException.getErrors().size() > 0) {
			throw pojoValException;
		}
	}
	
	/**
	 * Checking if we have validation errors, if we have them we throw them.
	 * 
	 * @throws PojoValidationException
	 */
	protected void checkValidationResults(PojoValidationException validationException) throws PojoValidationException {
		if (validationException != null && validationException.getErrors().size() > 0) {
			throw validationException;
		}
	}
	
	/**
	 * Validates an attachment properties.
	 * 
	 * @param attachment The attachment
	 */
	protected void validateAttachment(Attachment attachment, PojoValidationException validationException) {
		if (attachment.getFileName().equals("") || attachment.getFileName().isEmpty()) {
			validationException.addError("File name should not be empty.");
		}

		if (attachment.getDateSubmitted() == null) {
			validationException.addError("Date submitted should not be empty.");
		}

		if (attachment.getFileContent().equals(null)) {
			validationException.addError("You should upload a document.");
		}
	}

	/**
	 * Validating employee.
	 * 
	 * @param employee The employee.
	 * @throws PojoValidationException
	 */
	protected void validateEmployee(Employee employee, PojoValidationException validationException) {
		validateEmployeeUsername(employee.getUsername(), validationException);

		if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
			validationException.addError("First Name should not be empty.");
		}

		if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
			validationException.addError("Last Name should not be empty.");
		}
		
		if (employee.getEmail() == null  || employee.getEmail().isEmpty()) {
			validationException.addError("Email should not be empty.");
		}
		
		validateEmployeePassword(employee.getPassword(), validationException);

		validateSupervisorId(employee.getSupervisorId(), validationException);
	}
	
	/**
	 * Validating employee username.
	 * 
	 * @param username The username
	 */
	protected void validateEmployeeUsername(String username, PojoValidationException validationException) {
		if (username == null || username.isEmpty()) {
			validationException.addError("Username should not be empty.");
		}
	}

	/**
	 * Validating supervisorId.
	 * 
	 * @param supervisorId The id
	 */
	protected void validateSupervisorId(Integer supervisorId, PojoValidationException validationException) {
		if (supervisorId == null) {
			validationException.addError("Supervisor should be selected.");
		}
	}
	
	/**
	 * Validating employee password.
	 * 
	 * @param password The password
	 */
	protected void validateEmployeePassword(String password, PojoValidationException validationException) {
		if (password == null || password.isEmpty()) {
			validationException.addError("Password should not be empty.");
		}
	}

	/**
	 * Validating grade format properties.
	 * 
	 * @param gradingFormat The grading format
	 */
	protected void validateGradingFormat(GradingFormat gradingFormat) {
		cleanValidationResults();

		if (gradingFormat.getFromRange().equals("") || gradingFormat.getFromRange().isEmpty()) {
			pojoValException.addError("From should not be empty.");
		}

		if (gradingFormat.getToRange().equals("") || gradingFormat.getToRange().isEmpty()) {
			pojoValException.addError("To should not be empty.");
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
			pojoValException.addError("Please provide the information.");
		}
	}

	/**
	 * Validating reason denied properties.
	 * 
	 * @param reasonDenied The reason denied
	 */
	protected void validateReasonDenied(ReasonDenied reasonDenied) {
		cleanValidationResults();

		if (reasonDenied.getReason().equals("") || reasonDenied.getReason().isEmpty()) {
			pojoValException.addError("Please provide the reason why this request was denied.");
		}
	}

	/**
	 * Validating reason exceeding properties.
	 * 
	 * @param reasonExceeding The reason exceeding
	 */
	protected void validateReasonExceeding(ReasonExceeding reasonExceeding) {
		cleanValidationResults();

		if (reasonExceeding.getReason().equals("") || reasonExceeding.getReason().isEmpty()) {
			pojoValException.addError("Please provide the reason why the amount for this request was exceeding.");
		}
	}

	/**
	 * Validating the event.
	 * 
	 * @param event                  The event
	 * @param daysBeforeStartOfEvent Days before the event
	 * @param from                   Grade range (from)
	 * @param to                     Grade range (to)
	 */
	protected void validateEvent(Event event, int daysBeforeStartOfEvent, String from, String to) {
		if (event.getEmployeeId() == null) {
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}

		if (event.getDateOfEvent() == null) {
			pojoValException.addError("Date of the event is required.");
		}

		if (event.getTimeOfEvent() == null) {
			pojoValException.addError("Time of the event is required.");
		}

		if (event.getLocation().equals("") || event.getLocation().isEmpty()) {
			pojoValException.addError("Location of the event is required.");
		}

		if (event.getDescription().equals("") || event.getDescription().isEmpty()) {
			pojoValException.addError("Description of the event is required.");
		}

		if (event.getCost() <= 0) {
			pojoValException.addError("Cost of the event should be greater than 0.");
		}

		if (event.getGradingFormatId() == null) {
			if (from.equals("") || from.isEmpty() || (to.equals("") || to.isEmpty())) {
				LogUtilities.trace("The employee did not provide a gradding format or the grade for this event.");
				pojoValException.addError("You should select a grading format or enter a grading range [from - to].");
			}
		}

		if (event.getEventTypeId() == null) {
			pojoValException.addError("You should select the type of event.");
		}

		if (event.getWorkJustification().equals("") || event.getWorkJustification().isEmpty()) {
			pojoValException.addError("Work-related justification is required.");
		}

		if (daysBeforeStartOfEvent < 7) {
			LogUtilities.trace("Event is less than a week from begin.");

			pojoValException.addError("Your request could not be proccessed.\n"
					+ "You must complete the Tuition Reimbursement form one week prior to the start of the event.");
		}
	}

}

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
	 * it after validating everything we need
	 */
	protected PojoValidationException pojoValException;

	/**
	 * Checking if we have validation errors, if we have them we throw them.
	 * Convenience method.
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

		if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
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
	protected void validateGradingFormat(GradingFormat gradingFormat, PojoValidationException validationException) {
		if (gradingFormat.getFromRange() == null || gradingFormat.getFromRange().isEmpty()) {
			validationException.addError("From should not be empty.");
		}

		if (gradingFormat.getToRange() == null || gradingFormat.getToRange().isEmpty()) {
			validationException.addError("To should not be empty.");
		}
	}

	/**
	 * Validating information required properties.
	 * 
	 * @param informationRequired The information required
	 */
	protected void validateInformationRequired(InformationRequired informationRequired,
			PojoValidationException validationException) {

		if (informationRequired.getInformation() == null || informationRequired.getInformation().isEmpty()) {
			validationException.addError("Please provide the information.");
		}
	}

	/**
	 * Validating reason denied properties.
	 * 
	 * @param reasonDenied The reason denied
	 */
	protected void validateReasonDenied(ReasonDenied reasonDenied, PojoValidationException validationException) {

		if (reasonDenied.getReason() == null || reasonDenied.getReason().isEmpty()) {
			validationException.addError("Please provide the reason why this request was denied.");
		}
	}

	/**
	 * Validating reason exceeding properties.
	 * 
	 * @param reasonExceeding The reason exceeding
	 */
	protected void validateReasonExceeding(ReasonExceeding reasonExceeding,
			PojoValidationException validationException) {

		if (reasonExceeding.getReason() == null || reasonExceeding.getReason().isEmpty()) {
			validationException.addError("Please provide the reason why the amount for this request was exceeding.");
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
	protected void validateEvent(Event event, int daysBeforeStartOfEvent, String from, String to,
			PojoValidationException validationException) {

		if (event.getDateOfEvent() == null) {
			validationException.addError("Date of the event is required.");
		}

		if (event.getTimeOfEvent() == null) {
			validationException.addError("Time of the event is required.");
		}

		if (event.getLocation() == null || event.getLocation().isEmpty()) {
			validationException.addError("Location of the event is required.");
		}

		if (event.getDescription() == null || event.getDescription().isEmpty()) {
			validationException.addError("Description of the event is required.");
		}

		if (event.getCost() <= 0) {
			validationException.addError("Cost of the event should be greater than 0.");
		}
		
		if (event.getGradeCutoff() == null || event.getGradeCutoff().isEmpty()) {
			validationException.addError("Grade cutoff is required");
		}

		if (event.getEventTypeId() == null) {
			validationException.addError("You should select the type of event.");
		}

		if (event.getGradingFormatId() == null) {
			if (from == null|| from.isEmpty() || (to == null || to.isEmpty())) {
				LogUtilities.trace("The employee did not provide a gradding format or the grade for this event.");
				validationException
						.addError("You should select a grading format or enter a grading range [from - to].");
			}
		}

		if (event.getWorkJustification() == null|| event.getWorkJustification().isEmpty()) {
			validationException.addError("Work-related justification is required.");
		}

		if (daysBeforeStartOfEvent < 7) {
			LogUtilities.trace("Event is less than a week from begin.");

			validationException.addError("Your request could not be proccessed.\n"
					+ "You must complete the Tuition Reimbursement form one week prior to the start of the event.");
		}
	}

	protected void validateReimbursementChangeAmount(double newAmount, String reason,
			PojoValidationException validationException) {
		if (newAmount < 0) {
			LogUtilities.trace("Amount reimburse is negative.");

			validationException.addError("Amount should not be less than 0.");
		}

		if (reason == null || reason.isEmpty()) {
			validationException.addError("Reason for changin the projected amount is required.");
		}
	}

	protected void validateConfirmPassingGrade(Event event, PojoValidationException validationException) {
		if (!event.isRequiredPresentation() && event.getFinalGrade() == null || !event.getFinalGrade().isEmpty()) {
			LogUtilities.trace("Grade has not been provided.");
			validationException.addError("A passing grade has not been provided.");
		}
	}

	protected void validateConfirmSuccessfulPresentation(Event event, PojoValidationException validationException) {
		if (event.isRequiredPresentation() && !event.isPresentationUploaded()) {
			LogUtilities.trace("Presentation has not been uploaded.");
			pojoValException.addError("A presentation has not been uploaded.");
		}
	}

}

package com.revature.trms.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.daos.EventDAO;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.EvaluationResult;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.pojos.ReasonExceeding;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventServiceImpl extends BaseService implements EventService {

	private EventDAO eventDao;

	private EmployeeService employeeService;
	private EventTypeService eventTypeService;
	private GradingFormatService gradingFormatService;
	private AttachmentService attachmentService;
	private InformationRequiredService informationRequiredService;
	private ReasonDeniedService reasonDeniedService;
	private ReasonExceedingService reasonExceedingService;

	private final double AVAILABLE_REIMBURSEMENT_AMOUNT_PER_YEAR = 1000.00; // $1,000
	private final int DAYS_UNTIL_AUTOAPPROVED_REQUEST = 4; // 4 days before event to auto-approved the event

	public EventServiceImpl() {
		eventDao = DAOUtilities.geEventDAO();

		employeeService = ServiceUtilities.getEmployeeService();
		eventTypeService = ServiceUtilities.getEventTypeService();
		gradingFormatService = ServiceUtilities.getGradingFormatService();
		attachmentService = ServiceUtilities.getAttachmentService();
		informationRequiredService = ServiceUtilities.getInformationRequiredService();
		reasonDeniedService = ServiceUtilities.getReasonDeniedService();
		reasonExceedingService = ServiceUtilities.getReasonExceedingService();
	}

	@Override
	public boolean completeTuitionReimbursementForm(Event event, String from, String to, List<Attachment> attachments)
			throws PojoValidationException, IllegalParameterException {

		LogUtilities.trace("completeTuitionReimbursementForm");

		if (event.getEmployeeId() == null) {
			throw new IllegalParameterException("completeTuitionReimbursementForm - employeeId should not be empty.");
		}

		int daysBeforeStartOfEvent = 0;

		if (event.getDateOfEvent() != null) {
			daysBeforeStartOfEvent = Period.between(LocalDate.now(), event.getDateOfEvent().toLocalDate()).getDays();
			LogUtilities.trace("Days before start: " + daysBeforeStartOfEvent);
		}

		pojoValException = PojoValidationException.getInstance();
		// Validating the data for the event
		validateEvent(event, daysBeforeStartOfEvent, from, to, pojoValException);
		checkValidationResults(pojoValException);

		double projectedReimbursement = getProjectedReimbursementForEvent(event.getCost(), event.getEventTypeId());
		LogUtilities.trace("Projected Reimbursement: " + projectedReimbursement);

		double availableReimbursement = getAvailableReimbursementForEmployee(event.getEmployeeId());
		LogUtilities.trace("Available Reimbursement: " + availableReimbursement);

		if (projectedReimbursement > availableReimbursement) {
			LogUtilities.trace("Projected reimbursement > available Reimbursement.");

			projectedReimbursement = availableReimbursement;
		}

		event.setProjectedAmountReimbused(projectedReimbursement);

		setGradingFormat(event, from, to);

		if (daysBeforeStartOfEvent < Period.ofWeeks(2).getDays()) {
			LogUtilities.trace("The event is urgent.");
			event.setUrgent(true);
		}

		event.setPresentationUploaded(false);
		event.setAcceptedAmountReimbursed(0);
		event.setExceedsAvaliableFunds(false);
		event.setCanceledByEmployee(false);

		// Setting status for passing grade provided or presentation uploaded
		// This two variables will determine if the event is awarded or not
		event.setPassingGradeProvided(EvaluationResult.Pending);
		event.setSuccessfulPresentationProvided(EvaluationResult.Pending);

		// Setting if the event is pending, approve or rejected
		event.setReimbursementStatus(EventStatus.Pending);

		// Setting approval status
		event.setDsEventStatus(EventStatus.Pending); // Direct supervisor
		event.setHdEventStatus(EventStatus.Pending); // Head department
		event.setBencoEventStatus(EventStatus.Pending); // BenCo

		boolean added = eventDao.addEvent(event);

		if (added) {
			if (attachments != null) {
				addAttachmentsToEvent(event, attachments);

				LogUtilities.trace("Event added sucessfully");
			}

			return true;
		}

		LogUtilities.error("Event was not added to the system.");

		return false;
	}

	/**
	 * Setting a grading format for the event.
	 * 
	 * @param event The event
	 * @param from  The lower level of the grading format
	 * @param to    The upper level of the grading format
	 * @throws PojoValidationException Validation errors
	 */
	private void setGradingFormat(Event event, String from, String to) throws PojoValidationException {
		if (event.getGradingFormatId() == null) {
			LogUtilities.trace("Employee entered a new grading format.");

			GradingFormat gradingFormat = new GradingFormat(from, to);
			boolean added = gradingFormatService.addGradingFormat(gradingFormat);

			if (!added) {
				LogUtilities.error("Gradding format not added - completeTuitionReimbursementForm");
			}

			event.setGradingFormatId(gradingFormat.getGradingFormatId());
		}
	}

	/**
	 * Adding attachments to the event.
	 * 
	 * @param event       The event
	 * @param attachments Attachment to add
	 * @throws PojoValidationException   If there are validation errors
	 * @throws IllegalParameterException
	 */
	private void addAttachmentsToEvent(Event event, List<Attachment> attachments)
			throws PojoValidationException, IllegalParameterException {
		if (attachments.size() > 0) {
			LogUtilities.trace("There are attachments for this event");
			// If the employee provided an approval email from a DS or a DH
			for (Attachment attachment : attachments) {
				switch (attachment.getDocumentType()) {
				case Direct_Supervisor_Approval:
					LogUtilities.trace("Direct Supervisor approval attachment.");
					event.setDsEventStatus(EventStatus.Approved);
					break;
				case Department_Head_Approval:
					LogUtilities.trace("Department Head approval attachment.");
					event.setHdEventStatus(EventStatus.Approved);
					break;
				default:
					break;
				}

				attachment.setEventId(event.getEventId());

				attachmentService.addAttachment(attachment);
			}

			eventDao.updateEvent(event);

			LogUtilities.trace("Event update with the attachment.");
		}
	}

	@Override
	public double getAvailableReimbursementForEmployee(Integer employeeId) {
		LogUtilities.trace("getAvailableReimbursementForEmployee");

		double availableReimbursement = AVAILABLE_REIMBURSEMENT_AMOUNT_PER_YEAR;

		List<Event> eventsForEmployee = eventDao.getEventsNotDeniedByEmployeeAndYear(employeeId,
				LocalDate.now().getYear());

		for (Event event : eventsForEmployee) {
			if (event.getBencoEventStatus() == EventStatus.Pending) {
				availableReimbursement -= event.getProjectedAmountReimbused();

			} else if (event.getBencoEventStatus() == EventStatus.Approved
					&& (event.getPassingGradeProvided() == EvaluationResult.Yes
							|| event.getSuccessfulPresentationProvided() == EvaluationResult.Yes)) {

				availableReimbursement -= event.getAcceptedAmountReimbursed();
			}
		}

		LogUtilities.trace("Available reimbursement: " + availableReimbursement);

		return availableReimbursement;
	}

	@Override
	public double getProjectedReimbursementForEvent(double cost, Integer eventTypeId) throws IllegalParameterException {
		LogUtilities.trace("getProjectedReimbursementForEvent");

		EventType eventType = eventTypeService.getEventTypeById(eventTypeId);
		double percentage = eventType.getReimburseCoverage() / 100.0;

		LogUtilities.trace("Percentage: " + percentage);

		return cost * percentage;
	}

	@Override
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId)
			throws NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("approveTuitionReimbursementByDirectSupervisor");

		if (eventId == null) {
			throw new IllegalParameterException(
					"approveTuitionReimbursementByDirectSupervisor - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setDsEventStatus(EventStatus.Approved);

		Employee employee = employeeService.getEmployeeById(supervisorId);

		if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) {
			LogUtilities.trace("Direct Supervisor is also a Head Department");

			event.setHdEventStatus(EventStatus.Approved);
		}

		return eventDao.updateEvent(event);
	}

	@Override
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId)
			throws NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("approveTuitionReimbursementByHeadDepartment");

		if (eventId == null) {
			throw new IllegalParameterException(
					"approveTuitionReimbursementByHeadDepartment - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			LogUtilities.trace("Event not found");
			throw new NotFoundRecordException("Event not found.");
		}

		event.setHdEventStatus(EventStatus.Approved);

		return eventDao.updateEvent(event);
	}

	@Override
	public boolean approveTuitionReimbursementByBenCo(Integer eventId)
			throws NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("approveTuitionReimbursementByBenCo");

		if (eventId == null) {
			throw new IllegalParameterException("approveTuitionReimbursementByBenCo - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setBencoEventStatus(EventStatus.Approved);

		return eventDao.updateEvent(event);
	}

	@Override
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException,
			IllegalParameterException {
		LogUtilities.trace("denyTuitionReimbursementByDirectSupervisor");

		if (eventId == null) {
			throw new IllegalParameterException(
					"denyTuitionReimbursementByDirectSupervisor - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setDsEventStatus(EventStatus.Denied);
		event.setReimbursementStatus(EventStatus.Denied);

		boolean updated = eventDao.updateEvent(event);

		if (updated) {
			LogUtilities.trace("Event has been denied");

			ReasonDenied reasonDenied = new ReasonDenied(eventId, reason);

			return reasonDeniedService.addReasonDenied(reasonDenied);
		}

		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByHeadDepartment(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException,
			IllegalParameterException {
		LogUtilities.trace("denyTuitionReimbursementByHeadDepartment");

		if (eventId == null) {
			throw new IllegalParameterException(
					"denyTuitionReimbursementByHeadDepartment - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setHdEventStatus(EventStatus.Denied);
		event.setReimbursementStatus(EventStatus.Denied);

		return eventDao.updateEvent(event);
	}

	@Override
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, String reason) throws NotFoundRecordException,
			PreexistingRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("denyTuitionReimbursementByBenCo");

		if (eventId == null) {
			throw new IllegalParameterException("denyTuitionReimbursementByBenCo - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setBencoEventStatus(EventStatus.Denied);
		event.setReimbursementStatus(EventStatus.Denied);

		boolean updated = eventDao.updateEvent(event);

		if (updated) {
			ReasonDenied reasonDenied = new ReasonDenied(eventId, reason);

			return reasonDeniedService.addReasonDenied(reasonDenied);
		}

		return false;
	}

	public boolean requestInformationFromEmployee(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException,
			IllegalParameterException {
		LogUtilities.trace("requestInformationFromEmployee");

		validateInformationRequired(eventId, information, requiredBy);

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		LogUtilities.trace("Sending request for information.");

		return sendRequestForInformation(eventId, event.getEmployeeId(), information, requiredBy);
	}

	@Override
	public boolean requestInformationFromDirectSupervisor(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException,
			IllegalParameterException {
		LogUtilities.trace("requestInformationFromDirectSupervisor");

		validateInformationRequired(eventId, information, requiredBy);

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		Employee directSupervisor = employeeService.getEmployeeSupervisor(event.getEmployeeId());

		LogUtilities.trace(directSupervisor.toString());

		LogUtilities.trace("Sending request for information.");

		return sendRequestForInformation(eventId, directSupervisor.getEmployeeId(), information, requiredBy);
	}

	@Override
	public boolean requestInformationFromDepartmentHead(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException,
			IllegalParameterException {
		LogUtilities.trace("requestInformationFromDepartmentHead");

		validateInformationRequired(eventId, information, requiredBy);

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		Employee directSupervisor = employeeService.getEmployeeSupervisor(event.getEmployeeId());
		Employee departHead = employeeService.getEmployeeSupervisor(directSupervisor.getEmployeeId());

		LogUtilities.trace("Sending request for information.");

		return sendRequestForInformation(eventId, departHead.getEmployeeId(), information, requiredBy);
	}

	private void validateInformationRequired(Integer eventId, String information, Integer requiredBy)
			throws IllegalParameterException, PojoValidationException {
		if (eventId == null) {
			throw new IllegalParameterException("validateInformationRequired - eventId should not be empty");
		}

		if (requiredBy == null) {
			throw new IllegalParameterException("validateInformationRequired - requireBy should not be empty");
		}

		pojoValException = PojoValidationException.getInstance();

		if (information.equals("") || information.isEmpty()) {
			pojoValException.addError("You should provide the information require from this employe.");
		}

		checkValidationResults(pojoValException); // check validation results
	}

	private boolean sendRequestForInformation(Integer eventId, Integer employeeId, String information,
			Integer requiredBy) throws PojoValidationException, PreexistingRecordException, IllegalParameterException {

		InformationRequired informationRequired = new InformationRequired(eventId, employeeId, information, false,
				requiredBy);

		return informationRequiredService.addInformationRequired(informationRequired);
	}

	public void confirmSentOfInformationRequired(Integer eventId, Integer employeeId)
			throws NotFoundRecordException, IllegalParameterException, PojoValidationException {
		LogUtilities.trace("confirmSentOfInformationRequired");
		LogUtilities.debug("Emp id: " + employeeId);

		InformationRequired informationRequired = informationRequiredService
				.getInformationRequiredByEmployeeIdAndEventId(employeeId, eventId);

		LogUtilities.trace("confirmSentOfInformationRequired - " + informationRequired);

		if (informationRequired == null) {
			throw new NotFoundRecordException("We could not found this information.");
		}

		LogUtilities.trace("Updating Provided property of info");
		informationRequired.setProvided(true);

		informationRequiredService.updateInformationRequired(informationRequired);

		LogUtilities.trace("Confirm the information requested was sent");
	}

	@Override
	public void autoApproveEvents() {
		LogUtilities.debug("autoApproveEvents");

		// TODO Make this automatic every night
		// Approving events not approved by a direct supervisor in a timely matter
		List<Event> eventsPendingOfApproval;

		LogUtilities.trace("Approving Direct Supervisor's pending events");
		eventsPendingOfApproval = eventDao.getEventsPendingOfDirectSupervisorApproval();

		for (Event event : eventsPendingOfApproval) {
			int daysBeforeStartOfEvent = Period.between(LocalDate.now(), event.getDateOfEvent().toLocalDate())
					.getDays();

			if (daysBeforeStartOfEvent < Period.ofDays(DAYS_UNTIL_AUTOAPPROVED_REQUEST).getDays()) {
				event.setDsEventStatus(EventStatus.Approved);
				eventDao.updateEvent(event);
			}
		}

		// Approving events not approved by a department head in a timely matter
		LogUtilities.trace("Approving Head Deparment's pending events");
		eventsPendingOfApproval = eventDao.getEventsPendingOfHeadDepartmentApproval();

		for (Event event : eventsPendingOfApproval) {
			int daysBeforeStartOfEvent = Period.between(LocalDate.now(), event.getDateOfEvent().toLocalDate())
					.getDays();

			if (daysBeforeStartOfEvent < Period.ofDays(DAYS_UNTIL_AUTOAPPROVED_REQUEST).getDays()) {
				event.setHdEventStatus(EventStatus.Approved);
				eventDao.updateEvent(event);
			}
		}

		// TODO: Sending an escalation email to benco's direct supervisor, if request is
		// not approved in a timely matter
		LogUtilities.trace("Sending BenCo's manager about pending events");
		eventsPendingOfApproval = eventDao.getEventsPendingOfBenefitsCoordinatorApproval();

		for (Event event : eventsPendingOfApproval) {
			int daysBeforeStartOfEvent = Period.between(LocalDate.now(), event.getDateOfEvent().toLocalDate())
					.getDays();

			if (daysBeforeStartOfEvent < Period.ofDays(DAYS_UNTIL_AUTOAPPROVED_REQUEST).getDays()) {
				LogUtilities.trace("Sending email to BenCo's suppervisor");
			}
		}

	}

	@Override
	public boolean changeReimbursementAmount(Integer eventId, double newAmount, String reason)
			throws PojoValidationException, NotFoundRecordException, PreexistingRecordException,
			IllegalParameterException {
		LogUtilities.trace("changeReimbursementAmount");

		if (eventId == null) {
			throw new IllegalParameterException("validateInformationRequired - eventId should not be empty");
		}

		pojoValException = PojoValidationException.getInstance();
		validateReimbursementChangeAmount(newAmount, reason, pojoValException);
		checkValidationResults(pojoValException); // check validation results

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			LogUtilities.error("Event is null");
			throw new NotFoundRecordException("Event not found.");
		}

		if (newAmount > getAvailableReimbursementForEmployee(event.getEmployeeId())) {
			LogUtilities.trace("New amount exceeds available amount for employee");
			event.setExceedsAvaliableFunds(true);
		}

		event.setAcceptedAmountReimbursed(newAmount);

		boolean updated = eventDao.updateEvent(event);

		if (updated) {
			LogUtilities.trace("Amount change. Saving the reason of the amount change.");

			ReasonExceeding reasonExceeding = reasonExceedingService.getReasonExceedingByEventId(eventId);

			if (reasonExceeding == null) {
				reasonExceeding = new ReasonExceeding(eventId, reason);
				return reasonExceedingService.addReasonExceeding(reasonExceeding);
			}

			return reasonExceedingService.updateReasonExceeding(reasonExceeding);
		}

		return false;
	}

	@Override
	public boolean cancelReimbursementRequest(Integer eventId)
			throws NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("cancelReimbursementRequest");

		if (eventId == null) {
			throw new IllegalParameterException("cancelReimbursementRequest - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setCanceledByEmployee(true);

		LogUtilities.trace("Request has been canceled.");

		return eventDao.updateEvent(event);
	}
	
	@Override
	public void uploadEventApprovalAttachment(Integer eventId, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("uploadEventApprovalAttachment");
		
		Event event = eventDao.getEventById(eventId);
		List<Attachment> attachments = new ArrayList<>();
		attachments.add(attachment);
		
		
		addAttachmentsToEvent(event, attachments);
	}

	@Override
	public boolean uploadFinalGrade(Integer eventId, String finalGrade, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("uploadFinalGrade");

		if (eventId == null) {
			throw new IllegalParameterException("uploadFinalGrade - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setFinalGrade(finalGrade);

		boolean updated = eventDao.updateEvent(event);

		if (updated) {
			LogUtilities.trace("Final grade has been uploaded.");
			return attachmentService.addAttachment(attachment);
		}

		return false;
	}

	@Override
	public boolean uploadEventPresentation(Integer eventId, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("uploadEventPresentation");

		if (eventId == null) {
			throw new IllegalParameterException("uploadFinalGrade - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		event.setPresentationUploaded(true);

		boolean updated = eventDao.updateEvent(event);

		if (updated) {
			LogUtilities.trace("Event presentation has been uploaded.");
			return attachmentService.addAttachment(attachment);
		}

		return false;
	}

	@Override
	public boolean confirmPassingGrade(Integer eventId, boolean successful)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("confirmPassingGrade");

		if (eventId == null) {
			throw new IllegalParameterException("uploadFinalGrade - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		pojoValException = PojoValidationException.getInstance();
		validateConfirmPassingGrade(event, pojoValException);
		checkValidationResults(pojoValException);

		if (event.getAcceptedAmountReimbursed() == 0) {
			LogUtilities.trace("Awarded amount was not changed. Assigning projected amount.");
			event.setAcceptedAmountReimbursed(event.getProjectedAmountReimbused());
		}

		if (successful) {
			event.setPassingGradeProvided(EvaluationResult.Yes);
			event.setReimbursementStatus(EventStatus.Approved);

		} else {
			event.setPassingGradeProvided(EvaluationResult.No);
			event.setReimbursementStatus(EventStatus.Denied);
		}

		LogUtilities.trace("Passing grade confirmed.");

		return eventDao.updateEvent(event);
	}

	@Override
	public boolean confirmSuccessfulPresentation(Integer eventId, boolean successful)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException {
		LogUtilities.trace("confirmSuccessfulPresentation");

		if (eventId == null) {
			throw new IllegalParameterException("confirmSuccessfulPresentation - eventId should not be empty");
		}

		Event event = eventDao.getEventById(eventId);

		if (event == null) {
			throw new NotFoundRecordException("Event not found.");
		}

		pojoValException = PojoValidationException.getInstance();
		validateConfirmSuccessfulPresentation(event, pojoValException);
		checkValidationResults(pojoValException);

		if (event.getAcceptedAmountReimbursed() == 0) {
			LogUtilities.trace("Awarded amount was not changed. Assigning projected amount.");
			event.setAcceptedAmountReimbursed(event.getProjectedAmountReimbused());
		}

		if (successful) {
			event.setSuccessfulPresentationProvided(EvaluationResult.Yes);
			event.setReimbursementStatus(EventStatus.Approved);
		} else {
			event.setSuccessfulPresentationProvided(EvaluationResult.No);
			event.setReimbursementStatus(EventStatus.Denied);
		}

		LogUtilities.trace("Successful presentation was presented.");

		return eventDao.updateEvent(event);
	}

	@Override
	public Event getEventById(Integer eventId) throws IllegalParameterException {
		LogUtilities.trace("getEventById");

		if (eventId == null) {
			throw new IllegalParameterException("getEventById - eventId should not be empty");
		}

		return eventDao.getEventById(eventId);
	}

	@Override
	public List<Event> getEventsPendingOfDirectSupervisorApproval(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("getEventsPendingOfDirectSupervisorApproval");

		if (employeeId == null) {
			throw new IllegalParameterException(
					"getEventsPendingOfDirectSupervisorApproval - employeeId should not be empty");
		}

		List<Event> events = new ArrayList<>();

		// Getting events
		List<Event> eventsPendingOfDirectSupervisorApproval = eventDao.getEventsPendingOfDirectSupervisorApproval();

		// Getting employees under this supervisor
		List<Integer> employeesIdsUnderSup = employeeService.getEmployeesIdsUnderSupervisorId(employeeId);
		LogUtilities.trace(employeesIdsUnderSup.toString());

		for (Event evt : eventsPendingOfDirectSupervisorApproval) {
			if (employeesIdsUnderSup.contains(evt.getEmployeeId())) {
				LogUtilities.trace("Add event: " + evt);
				events.add(evt);
			}
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfHeadDepartmentApproval(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("getEventsPendingOfHeadDepartmentApproval");

		if (employeeId == null) {
			throw new IllegalParameterException(
					"getEventsPendingOfHeadDepartmentApproval - employeeId should not be empty");
		}

		List<Event> events = new ArrayList<>();

		// Getting all the pending events of department head approval
		List<Event> eventsPendingOfHDApproval = eventDao.getEventsPendingOfHeadDepartmentApproval();
		// Getting employees under this supervisor
		List<Integer> employeesIdsUnderSup = new ArrayList<>();

		// looping the employee ladder
		for (Integer id : employeeService.getEmployeesIdsUnderSupervisorId(employeeId)) {
			employeesIdsUnderSup.add(id);

			for (Integer asoccId : employeeService.getEmployeesIdsUnderSupervisorId(id)) {
				employeesIdsUnderSup.add(asoccId);
			}
		}

		LogUtilities.trace("emp Ids: " + employeesIdsUnderSup);

		for (Event evt : eventsPendingOfHDApproval) {
			if (employeesIdsUnderSup.contains(evt.getEmployeeId())) {
				events.add(evt);
			}
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval() {
		LogUtilities.trace("getEventsPendingOfBenefitsCoordinatorApproval");

		return eventDao.getEventsPendingOfBenefitsCoordinatorApproval();
	}

	@Override
	public List<Event> getEventsByEmployeeId(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("getEventsByEmployeeId");

		if (employeeId == null) {
			throw new IllegalParameterException("getEventsByEmployeeId - employeeId should not be empty");
		}

		List<Event> events = eventDao.getEventsByEmployeeId(employeeId);

		return events;
	}

}

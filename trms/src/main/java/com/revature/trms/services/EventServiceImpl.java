package com.revature.trms.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.daos.EventDAO;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.EvaluationResult;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;
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
			throws PojoValidationException {

		LogUtilities.trace("completeTuitionReimbursementForm");

		int daysBeforeStartOfEvent = Period.between(LocalDate.now(), event.getDateOfEvent()).getDays();

		LogUtilities.trace("Days before start: " + daysBeforeStartOfEvent);

		validateEvent(event, daysBeforeStartOfEvent, from, to);

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

		// Setting status for grade provided or presentation uploaded
		// This two variables will determine if the event is awarded or not
		event.setPassingGradeProvided(EvaluationResult.Pending);
		event.setSuccessfulPresentationProvided(EvaluationResult.Pending);

		// Setting approval status
		event.setDsEventStatus(EventStatus.Pending); // Direct supervisor
		event.setHdEventStatus(EventStatus.Pending); // Head department
		event.setBencoEventStatus(EventStatus.Pending); // BenCo

		boolean added = eventDao.addEvent(event);

		if (!added) {
			LogUtilities.error("Event was not added to the system.");
			return false;
		}

		addAttachmentsToEvent(event, attachments);

		LogUtilities.trace("Event added sucessfully");

		return true;
	}

	@Override
	public double getAvailableReimbursementForEmployee(Integer employeeId) {
		LogUtilities.trace("getAvailableReimbursementForEmployee");
		double availableReimburstment = AVAILABLE_REIMBURSEMENT_AMOUNT_PER_YEAR;

		List<Event> eventsForEmployee = eventDao.getEventsNotDeniedByEmployeeAndYear(employeeId,
				LocalDate.now().getYear());

		for (Event event : eventsForEmployee) {
			if (event.getBencoEventStatus() == EventStatus.Pending) {
				availableReimburstment -= event.getProjectedAmountReimbused();

			} else if (event.getBencoEventStatus() == EventStatus.Approved
					&& (event.getPassingGradeProvided() == EvaluationResult.Yes
							|| event.getSuccessfulPresentationProvided() == EvaluationResult.Yes)) {

				availableReimburstment -= event.getAcceptedAmountReimbursed();
			}
		}

		return availableReimburstment;
	}

	@Override
	public double getProjectedReimbursementForEvent(double cost, Integer eventTypeId) {
		LogUtilities.trace("getProjectedReimbursementForEvent");

		EventType eventType = eventTypeService.getEventTypeById(eventTypeId);
		double percentage = eventType.getReimburseCoverage() / 100.0;

		LogUtilities.trace("Percentage: " + percentage);

		return cost * percentage;
	}

	@Override
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId, Integer supervisorId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean approveTuitionReimbursementByBenCo(Integer eventId, Integer supervisorId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId, String reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByHeadDepartment(Integer eventId, Integer supervisorId, String reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, Integer supervisorId, String reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requestAdditionalInformation(Integer fromEmployee, Integer toEmployee, String information) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean autoApproveEvents() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeReimbursementAmount(Integer eventId, double newAmount, String reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean uploadGradeOrPresentation(Integer eventId, Attachment attachment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean confirmPassingGrade(Integer eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean confirmSuccessfulPresentation(Integer eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Event getEventById(Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsPendingOfDirectSupervisorApproval(Integer employeeId) throws PojoValidationException {
		if (employeeId == null) {
			LogUtilities.error("EmployeeId should not be empty.");
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}

		List<Event> events = new ArrayList<>();

		// Getting events
		List<Event> eventsPendingOfDirectSupervisorApproval = eventDao.getEventsPendingOfDirectSupervisorApproval();
		// Getting employees under this supervisor
		List<Integer> employeesIdsUnderSup = employeeService.getEmployeesIdsUnderSupervisorId(employeeId);

		for (Event evt : eventsPendingOfDirectSupervisorApproval) {
			if (employeesIdsUnderSup.contains(evt.getEmployeeId())) {
				events.add(evt);
			}
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfHeadDepartmentApproval(Integer employeeId) throws PojoValidationException {
		if (employeeId == null) {
			LogUtilities.error("EmployeeId should not be empty.");
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}

		List<Event> events = new ArrayList<>();

		// Getting events
		List<Event> eventsPendingOfDirectSupervisorApproval = eventDao.getEventsPendingOfHeadDepartmentApproval();
		// Getting employees under this supervisor
		List<Integer> employeesIdsUnderSup = employeeService.getEmployeesIdsUnderSupervisorId(employeeId);

		for (Event evt : eventsPendingOfDirectSupervisorApproval) {
			if (employeesIdsUnderSup.contains(evt.getEmployeeId())) {
				events.add(evt);
			}
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval(Integer employeeId)
			throws PojoValidationException {
		if (employeeId == null) {
			LogUtilities.error("EmployeeId should not be empty.");
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}

		List<Event> events = new ArrayList<>();

		// Getting events
		List<Event> eventsPendingOfDirectSupervisorApproval = eventDao.getEventsPendingOfBenefitsCoordinatorApproval();
		// Getting employees under this supervisor
		List<Integer> employeesIdsUnderSup = employeeService.getEmployeesIdsUnderSupervisorId(employeeId);

		for (Event evt : eventsPendingOfDirectSupervisorApproval) {
			if (employeesIdsUnderSup.contains(evt.getEmployeeId())) {
				events.add(evt);
			}
		}

		return events;
	}

	/**
	 * Adding attachments to the event.
	 * 
	 * @param event       The event
	 * @param attachments Attachment to add
	 * @throws PojoValidationException If there are validation errors
	 */
	private void addAttachmentsToEvent(Event event, List<Attachment> attachments) throws PojoValidationException {
		if (attachments.size() > 0) {
			LogUtilities.trace("There are attachments for this event");
			// If the employee provided an approval email from a DS or a HD
			for (Attachment attachment : attachments) {
				if (attachment.isApprovalDoc()) {
					switch (attachment.getApprovalStage()) {
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
				}

				attachment.setEventId(event.getEventId());

				attachmentService.addAttachment(attachment);
			}

			eventDao.updateEvent(event);

			LogUtilities.trace("Event update with the attachment.");
		}
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

}

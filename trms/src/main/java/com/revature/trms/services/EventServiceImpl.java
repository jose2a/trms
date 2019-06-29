package com.revature.trms.services;

import java.time.LocalDate;
import java.time.Period;
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

		if (event.getEmployeeId() == null) {
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}

		if (event.getDateOfEvent() == null) {
			pojoValidationException.addError("Date of the event is required.");
		}

		if (event.getTimeOfEvent() == null) {
			pojoValidationException.addError("Time of the event is required.");
		}

		if (event.getLocation().equals("") || event.getLocation().isEmpty()) {
			pojoValidationException.addError("Location of the event is required.");
		}

		if (event.getDescription().equals("") || event.getDescription().isEmpty()) {
			pojoValidationException.addError("Description of the event is required.");
		}

		if (event.getCost() <= 0) {
			pojoValidationException.addError("Cost of the event should be greater than 0.");
		}

		if (event.getGradingFormatId() == null) {
			if (from.equals("") || from.isEmpty() || (to.equals("") || to.isEmpty())) {
				LogUtilities.trace("The employee did not provide a gradding format or the grade for this event.");
				pojoValidationException
						.addError("You should select a grading format or enter a grading range [from - to].");
			}
		}

		if (event.getEventTypeId() == null) {
			pojoValidationException.addError("You should select the type of event.");
		}

		if (event.getWorkJustification().equals("") || event.getWorkJustification().isEmpty()) {
			pojoValidationException.addError("Work-related justification is required.");
		}

		int daysBeforeStartOfEvent = Period.between(LocalDate.now(), event.getDateOfEvent()).getDays();
		
		LogUtilities.trace("Days before start: " + daysBeforeStartOfEvent);

		if (daysBeforeStartOfEvent < 7) {
			LogUtilities.trace("Event is less than a week from begin.");
			
			pojoValidationException.addError("Your request could not be proccessed.\n"
					+ "You must complete the Tuition Reimbursement form one week prior to the start of the event.");
		}

		double projectedReimbursement = getProjectedReimbursementForEvent(event.getCost(), event.getEventTypeId());
		LogUtilities.trace("Projected Reimbursement: " + projectedReimbursement);
		
		double availableReimbursement = getAvailableReimbursementForEmployee(event.getEmployeeId());
		LogUtilities.trace("Available Reimbursement: " + availableReimbursement);

		if (projectedReimbursement > availableReimbursement) {
			LogUtilities.trace("Projected reimbursement > available Reimbursement.");
			
			projectedReimbursement = availableReimbursement;
		}

		event.setProjectedAmountReimbused(projectedReimbursement);

		if (event.getGradingFormatId() == null) {
			LogUtilities.trace("Employee entered a new grading format.");
			
			GradingFormat gradingFormat = new GradingFormat(from, to);
			boolean added = gradingFormatService.addGradingFormat(gradingFormat);

			if (!added) {
				LogUtilities.error("Gradding format not added - completeTuitionReimbursementForm");
				
				return false;
			}

			event.setGradingFormatId(gradingFormat.getGradingFormatId());
		}

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
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean approveTuitionReimbursementByBenCo(Integer eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, String reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByHeadDepartmentr(Integer eventId, String reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, String reason) {
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
	public boolean awardAmount(Integer eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Event getEventById(Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsPendingOfDirectSupervisorApproval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsPendingOfHeadDepartmentApproval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEventDao(EventDAO eventDao) {
		this.eventDao = eventDao;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setEventTypeService(EventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}

	public void setGradingFormatService(GradingFormatService gradingFormatService) {
		this.gradingFormatService = gradingFormatService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setInformationRequiredService(InformationRequiredService informationRequiredService) {
		this.informationRequiredService = informationRequiredService;
	}

	public void setReasonDeniedService(ReasonDeniedService reasonDeniedService) {
		this.reasonDeniedService = reasonDeniedService;
	}

	public void setReasonExceedingService(ReasonExceedingService reasonExceedingService) {
		this.reasonExceedingService = reasonExceedingService;
	}

}

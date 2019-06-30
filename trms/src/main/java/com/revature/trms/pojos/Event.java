package com.revature.trms.pojos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Event {

	private Integer eventId;

	// Required information from employee
	private LocalDate dateOfEvent;
	private LocalTime timeOfEvent;
	private String location;
	private String description;
	private Double cost;
	private String workJustification;
	private boolean requiredPresentation;
	private String gradeCutoff;

	// User (optional)
	private int workTimeMissed;

	// Used for business logic
	private String finalGrade;
	private boolean presentationUploaded;

	private double projectedAmountReimbused;
	private double acceptedAmountReimbursed;

	private boolean isUrgent;
	private boolean exceedsAvaliableFunds;
	private boolean isCanceledByEmployee;

	private EventStatus reimbursementStatus;

	// DS, BenCo
	private EvaluationResult passingGradeProvided;
	private EvaluationResult successfulPresentationProvided;

	// Relationship fields
	private Integer employeeId;
	private Integer eventTypeId;
	private Integer gradingFormatId;

	private EventStatus dsEventStatus;
	private EventStatus hdEventStatus;
	private EventStatus bencoEventStatus;

	private Employee employee;
	private EventType eventType;
	private GradingFormat gradingFormat;
	private List<Attachment> attachments;

	public Event() {
		super();
	}

	/**
	 * Event Constructor.
	 * 
	 * @param dateOfEvent          Date when the event is taking place
	 * @param timeOfEvent          Time of the event
	 * @param location             Location of the event
	 * @param description          Description of the event
	 * @param cost                 Cost of the event
	 * @param workJustification    Work-related justification
	 * @param requiredPresentation This event required presentation
	 * @param gradeCutoff          Grade which is the minimum required to pass the
	 *                             event
	 */
	public Event(LocalDate dateOfEvent, LocalTime timeOfEvent, String location, String description, Double cost,
			String workJustification, boolean requiredPresentation, String gradeCutoff) {
		super();
		this.dateOfEvent = dateOfEvent;
		this.timeOfEvent = timeOfEvent;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.workJustification = workJustification;
		this.requiredPresentation = requiredPresentation;
		this.gradeCutoff = gradeCutoff;
	}

	/**
	 * Getting id for the event.
	 * 
	 * @return The event id
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * Setting id for the event.
	 * 
	 * @param eventId The event id
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * Getting Date when the event is going to take place.
	 * 
	 * @return The date
	 */
	public LocalDate getDateOfEvent() {
		return dateOfEvent;
	}

	/**
	 * Setting Date when the event is going to take place.
	 * 
	 * @param dateOfEvent The date
	 */
	public void setDateOfEvent(LocalDate dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	/**
	 * Getting Time of the event.
	 * 
	 * @return The time
	 */
	public LocalTime getTimeOfEvent() {
		return timeOfEvent;
	}

	/**
	 * Setting time of the event.
	 * 
	 * @param timeOfEvent The time
	 */
	public void setTimeOfEvent(LocalTime timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}

	/**
	 * Getting location where the event is taking place.
	 * 
	 * @return The location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setting location where the event is taking place.
	 * 
	 * @param location The location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getWorkJustification() {
		return workJustification;
	}

	public void setWorkJustification(String workJustification) {
		this.workJustification = workJustification;
	}

	public int getWorkTimeMissed() {
		return workTimeMissed;
	}

	public void setWorkTimeMissed(int workTimeMissed) {
		this.workTimeMissed = workTimeMissed;
	}

	/**
	 * Getting location where the event is taking place.
	 * 
	 * @return true/false
	 */
	public boolean isRequiredPresentation() {
		return requiredPresentation;
	}

	/**
	 * Getting location where the event is taking place.
	 * 
	 * @param requiredPresentation true/false
	 */
	public void setRequiredPresentation(boolean requiredPresentation) {
		this.requiredPresentation = requiredPresentation;
	}

	/**
	 * The minimum grade required to get the reimbursement.
	 * 
	 * @return minimum grade required
	 */
	public String getGradeCutoff() {
		return gradeCutoff;
	}

	/**
	 * The minimum grade required to get the reimbursement.
	 * 
	 * @param gradeCutoff minimum grade required
	 */
	public void setGradeCutoff(String gradeCutoff) {
		this.gradeCutoff = gradeCutoff;
	}

	/**
	 * The final grade obtained by the student after the event.
	 * 
	 * @return The final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * The final grade obtained by the student after the event.
	 * 
	 * @param finalGrade The final grade
	 */
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Tells if the presentation was uploaded, if a presentation was required for
	 * the event.
	 * 
	 * @return Presentation uploaded
	 */
	public boolean isPresentationUploaded() {
		return presentationUploaded;
	}

	/**
	 * Tells if the presentation was uploaded, if a presentation was required for
	 * the event.
	 * 
	 * @param presentationUploaded true/false
	 */
	public void setPresentationUploaded(boolean presentationUploaded) {
		this.presentationUploaded = presentationUploaded;
	}

	/**
	 * Get projected amount reimbursed for the event.
	 * 
	 * @return Projected amount
	 */
	public double getProjectedAmountReimbused() {
		return projectedAmountReimbused;
	}

	/**
	 * Get projected amount reimbursed for the event.
	 * 
	 * @param projectedAmountReimbused Projected amount
	 */
	public void setProjectedAmountReimbused(double projectedAmountReimbused) {
		this.projectedAmountReimbused = projectedAmountReimbused;
	}

	/**
	 * Amount reimbursed after the BenCo has approved the event.
	 * 
	 * @return Amount reimbursed
	 */
	public double getAcceptedAmountReimbursed() {
		return acceptedAmountReimbursed;
	}

	/**
	 * Amount reimbursed after the Benco has approved the event.
	 * 
	 * @param acceptedAmountReimbursed Amount reimbursed
	 */
	public void setAcceptedAmountReimbursed(double acceptedAmountReimbursed) {
		this.acceptedAmountReimbursed = acceptedAmountReimbursed;
	}

	/**
	 * The course is < 2 weeks from beginning. (Marker)
	 * 
	 * @return true/false
	 */
	public boolean isUrgent() {
		return isUrgent;
	}

	/**
	 * The course is < 2 weeks from beginning. (Marker)
	 * 
	 * @param isUrgent true/false
	 */
	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	/**
	 * If the BenCo assigned more money than what was projected. (Marker)
	 * 
	 * @return true/false
	 */
	public boolean isExceedsAvaliableFunds() {
		return exceedsAvaliableFunds;
	}

	/**
	 * If the BenCo assigned more money than what was projected. (Marker)
	 * 
	 * @param exceedsAvaliableFunds true/false
	 */
	public void setExceedsAvaliableFunds(boolean exceedsAvaliableFunds) {
		this.exceedsAvaliableFunds = exceedsAvaliableFunds;
	}

	/**
	 * The event was canceled by the employee.
	 * 
	 * @return true/false
	 */
	public boolean isCanceledByEmployee() {
		return isCanceledByEmployee;
	}

	/**
	 * The event was canceled by the employee.
	 * 
	 * @param isCanceledByEmployee true/false
	 */
	public void setCanceledByEmployee(boolean isCanceledByEmployee) {
		this.isCanceledByEmployee = isCanceledByEmployee;
	}

	/**
	 * The reimbursement was Approved, Denied or Pending.
	 * 
	 * @return Reimbursement status
	 */
	public EventStatus getReimbursementStatus() {
		return reimbursementStatus;
	}

	/**
	 * Setting reimbursement status (Approved, Denied, or Pending).
	 * 
	 * @param reimbursementStatus The status
	 */
	public void setReimbursementStatus(EventStatus reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	/**
	 * Tells if the grade provided by the employee was satisfactory.
	 * 
	 * @return (Pending, Yes, No)
	 */
	public EvaluationResult getPassingGradeProvided() {
		return passingGradeProvided;
	}

	/**
	 * Tells if the grade provided by the employee was satisfactory.
	 * 
	 * @param (Pending, Yes, No)
	 */
	public void setPassingGradeProvided(EvaluationResult passingGradeProvided) {
		this.passingGradeProvided = passingGradeProvided;
	}

	/**
	 * Tells if the presentation was satisfactory, if required presentation was
	 * true.
	 * 
	 * @return (Pending, Yes, No)
	 */
	public EvaluationResult getSuccessfulPresentationProvided() {
		return successfulPresentationProvided;
	}

	/**
	 * Tells if the presentation was satisfactory, if required presentation was
	 * true.
	 * 
	 * @param successfulPresentationProvided (Pending, Yes, No)
	 */
	public void setSuccessfulPresentationProvided(EvaluationResult successfulPresentationProvided) {
		this.successfulPresentationProvided = successfulPresentationProvided;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public Integer getGradingFormatId() {
		return gradingFormatId;
	}

	public void setGradingFormatId(Integer gradingFormatId) {
		this.gradingFormatId = gradingFormatId;
	}

	public EventStatus getDsEventStatus() {
		return dsEventStatus;
	}

	public void setDsEventStatus(EventStatus dsEventStatus) {
		this.dsEventStatus = dsEventStatus;
	}

	public EventStatus getHdEventStatus() {
		return hdEventStatus;
	}

	public void setHdEventStatus(EventStatus hdEventStatus) {
		this.hdEventStatus = hdEventStatus;
	}

	public EventStatus getBencoEventStatus() {
		return bencoEventStatus;
	}

	public void setBencoEventStatus(EventStatus bencoEventStatus) {
		this.bencoEventStatus = bencoEventStatus;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public GradingFormat getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(GradingFormat gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((dateOfEvent == null) ? 0 : dateOfEvent.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((eventTypeId == null) ? 0 : eventTypeId.hashCode());
		result = prime * result + ((gradeCutoff == null) ? 0 : gradeCutoff.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((timeOfEvent == null) ? 0 : timeOfEvent.hashCode());
		result = prime * result + ((workJustification == null) ? 0 : workJustification.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (dateOfEvent == null) {
			if (other.dateOfEvent != null)
				return false;
		} else if (!dateOfEvent.equals(other.dateOfEvent))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (eventTypeId == null) {
			if (other.eventTypeId != null)
				return false;
		} else if (!eventTypeId.equals(other.eventTypeId))
			return false;
		if (gradeCutoff == null) {
			if (other.gradeCutoff != null)
				return false;
		} else if (!gradeCutoff.equals(other.gradeCutoff))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (timeOfEvent == null) {
			if (other.timeOfEvent != null)
				return false;
		} else if (!timeOfEvent.equals(other.timeOfEvent))
			return false;
		if (workJustification == null) {
			if (other.workJustification != null)
				return false;
		} else if (!workJustification.equals(other.workJustification))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", dateOfEvent=" + dateOfEvent + ", timeOfEvent=" + timeOfEvent
				+ ", location=" + location + ", description=" + description + ", cost=" + cost + ", workJustification="
				+ workJustification + ", requiredPresentation=" + requiredPresentation + ", gradeCutoff=" + gradeCutoff
				+ ", workTimeMissed=" + workTimeMissed + ", finalGrade=" + finalGrade + ", presentationUploaded="
				+ presentationUploaded + ", projectedAmountReimbused=" + projectedAmountReimbused
				+ ", acceptedAmountReimbursed=" + acceptedAmountReimbursed + ", isUrgent=" + isUrgent
				+ ", exceedsAvaliableFunds=" + exceedsAvaliableFunds + ", isCanceledByEmployee=" + isCanceledByEmployee
				+ ", reimbursementStatus=" + reimbursementStatus + ", passingGradeProvided=" + passingGradeProvided
				+ ", successfulPresentationProvided=" + successfulPresentationProvided + ", employeeId=" + employeeId
				+ ", eventTypeId=" + eventTypeId + ", gradingFormatId=" + gradingFormatId + ", dsEventStatus="
				+ dsEventStatus + ", hdEventStatus=" + hdEventStatus + ", bencoEventStatus=" + bencoEventStatus + "]";
	}

}

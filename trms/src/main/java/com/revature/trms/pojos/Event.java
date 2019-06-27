package com.revature.trms.pojos;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

	private Integer eventId;

	// Required information from employee
	private LocalDate dateOfEvent;
	private LocalTime timeOfEvent;
	private String location;
	private String description;
	private double cost;
	private String workJustification;
	private int workTimeMissed;
	private boolean requiredPresentation;
	private String gradeCutoff;

	// Used for business logic
	private double projectedAmountReimbused;
	private double acceptedAmountReimbursed;
	private boolean isUrgent;
	private boolean exceedsAvaliableFunds;
	private boolean isCanceledByEmployee;

	// DS, BenCo
	private EvaluationResult passingGradeProvided;
	private EvaluationResult successfulPresentationProvided;

	// Relationship fields
	private int employeeId;
	private int eventTypeId;
	private int gradingFormatId;

	private EventStatus dsEventStatus;
	private EventStatus hdEventStatus;
	private EventStatus bencoEventStatus;

	private ApprovalStage approvalStage;

	public Event() {
		super();
	}

	public Event(LocalDate dateOfEvent, LocalTime timeOfEvent, String location, String description, double cost,
			String workJustification, int workTimeMissed, boolean requiredPresentation, String gradeCutoff) {
		super();
		this.dateOfEvent = dateOfEvent;
		this.timeOfEvent = timeOfEvent;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.workJustification = workJustification;
		this.workTimeMissed = workTimeMissed;
		this.requiredPresentation = requiredPresentation;
		this.gradeCutoff = gradeCutoff;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public LocalDate getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(LocalDate dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	public LocalTime getTimeOfEvent() {
		return timeOfEvent;
	}

	public void setTimeOfEvent(LocalTime timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
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

	public boolean isRequiredPresentation() {
		return requiredPresentation;
	}

	public void setRequiredPresentation(boolean requiredPresentation) {
		this.requiredPresentation = requiredPresentation;
	}

	public String getGradeCutoff() {
		return gradeCutoff;
	}

	public void setGradeCutoff(String gradeCutoff) {
		this.gradeCutoff = gradeCutoff;
	}

	public double getProjectedAmountReimbused() {
		return projectedAmountReimbused;
	}

	public void setProjectedAmountReimbused(double projectedAmountReimbused) {
		this.projectedAmountReimbused = projectedAmountReimbused;
	}

	public double getAcceptedAmountReimbursed() {
		return acceptedAmountReimbursed;
	}

	public void setAcceptedAmountReimbursed(double acceptedAmountReimbursed) {
		this.acceptedAmountReimbursed = acceptedAmountReimbursed;
	}

	public boolean isUrgent() {
		return isUrgent;
	}

	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public boolean isExceedsAvaliableFunds() {
		return exceedsAvaliableFunds;
	}

	public void setExceedsAvaliableFunds(boolean exceedsAvaliableFunds) {
		this.exceedsAvaliableFunds = exceedsAvaliableFunds;
	}

	public boolean isCanceledByEmployee() {
		return isCanceledByEmployee;
	}

	public void setCanceledByEmployee(boolean isCanceledByEmployee) {
		this.isCanceledByEmployee = isCanceledByEmployee;
	}

	public EvaluationResult getPassingGradeProvided() {
		return passingGradeProvided;
	}

	public void setPassingGradeProvided(EvaluationResult passingGradeProvided) {
		this.passingGradeProvided = passingGradeProvided;
	}

	public EvaluationResult getSuccessfulPresentationProvided() {
		return successfulPresentationProvided;
	}

	public void setSuccessfulPresentationProvided(EvaluationResult successfulPresentationProvided) {
		this.successfulPresentationProvided = successfulPresentationProvided;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public int getGradingFormatId() {
		return gradingFormatId;
	}

	public void setGradingFormatId(int gradingFormatId) {
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

	public ApprovalStage getApprovalStage() {
		return approvalStage;
	}

	public void setApprovalStage(ApprovalStage approvalStage) {
		this.approvalStage = approvalStage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(acceptedAmountReimbursed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((approvalStage == null) ? 0 : approvalStage.hashCode());
		result = prime * result + ((bencoEventStatus == null) ? 0 : bencoEventStatus.hashCode());
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateOfEvent == null) ? 0 : dateOfEvent.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dsEventStatus == null) ? 0 : dsEventStatus.hashCode());
		result = prime * result + employeeId;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + eventTypeId;
		result = prime * result + (exceedsAvaliableFunds ? 1231 : 1237);
		result = prime * result + ((gradeCutoff == null) ? 0 : gradeCutoff.hashCode());
		result = prime * result + gradingFormatId;
		result = prime * result + ((hdEventStatus == null) ? 0 : hdEventStatus.hashCode());
		result = prime * result + (isCanceledByEmployee ? 1231 : 1237);
		result = prime * result + (isUrgent ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((passingGradeProvided == null) ? 0 : passingGradeProvided.hashCode());
		temp = Double.doubleToLongBits(projectedAmountReimbused);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (requiredPresentation ? 1231 : 1237);
		result = prime * result
				+ ((successfulPresentationProvided == null) ? 0 : successfulPresentationProvided.hashCode());
		result = prime * result + ((timeOfEvent == null) ? 0 : timeOfEvent.hashCode());
		result = prime * result + ((workJustification == null) ? 0 : workJustification.hashCode());
		result = prime * result + workTimeMissed;
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
		if (Double.doubleToLongBits(acceptedAmountReimbursed) != Double
				.doubleToLongBits(other.acceptedAmountReimbursed))
			return false;
		if (approvalStage != other.approvalStage)
			return false;
		if (bencoEventStatus != other.bencoEventStatus)
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
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
		if (dsEventStatus != other.dsEventStatus)
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (eventTypeId != other.eventTypeId)
			return false;
		if (exceedsAvaliableFunds != other.exceedsAvaliableFunds)
			return false;
		if (gradeCutoff == null) {
			if (other.gradeCutoff != null)
				return false;
		} else if (!gradeCutoff.equals(other.gradeCutoff))
			return false;
		if (gradingFormatId != other.gradingFormatId)
			return false;
		if (hdEventStatus != other.hdEventStatus)
			return false;
		if (isCanceledByEmployee != other.isCanceledByEmployee)
			return false;
		if (isUrgent != other.isUrgent)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (passingGradeProvided != other.passingGradeProvided)
			return false;
		if (Double.doubleToLongBits(projectedAmountReimbused) != Double
				.doubleToLongBits(other.projectedAmountReimbused))
			return false;
		if (requiredPresentation != other.requiredPresentation)
			return false;
		if (successfulPresentationProvided != other.successfulPresentationProvided)
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
		if (workTimeMissed != other.workTimeMissed)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", dateOfEvent=" + dateOfEvent + ", timeOfEvent=" + timeOfEvent
				+ ", location=" + location + ", description=" + description + ", cost=" + cost + ", workJustification="
				+ workJustification + ", workTimeMissed=" + workTimeMissed + ", requiredPresentation="
				+ requiredPresentation + ", gradeCutoff=" + gradeCutoff + ", projectedAmountReimbused="
				+ projectedAmountReimbused + ", acceptedAmountReimbursed=" + acceptedAmountReimbursed + ", isUrgent="
				+ isUrgent + ", exceedsAvaliableFunds=" + exceedsAvaliableFunds + ", isCanceledByEmployee="
				+ isCanceledByEmployee + ", passingGradeProvided=" + passingGradeProvided
				+ ", successfulPresentationProvided=" + successfulPresentationProvided + ", employeeId=" + employeeId
				+ ", eventTypeId=" + eventTypeId + ", gradingFormatId=" + gradingFormatId + ", dsEventStatus="
				+ dsEventStatus + ", hdEventStatus=" + hdEventStatus + ", bencoEventStatus=" + bencoEventStatus
				+ ", approvalStage=" + approvalStage + "]";
	}

}

package com.revature.trms.pojos;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

	private int eventId;

	// Required information from employee
	private LocalDate dateSubmitted;
	private LocalTime timeSubmitted;
	private String location;
	private String description;
	private double cost;
	private String workJustification;
	private int workTimeMissed;
	private boolean requiredPresentation;
	private int gradeCutoffId;

	// Used for business logic
	private double projectedAmountReimbused;
	private double acceptedAmountReimbursed;
	private boolean isUrgent;
	private boolean exceedsAvaliableFunds;
	private boolean isCanceledByEmployee;

	// DS, BenCo
	private boolean passingGradeProvided;
	private boolean successfulPresentationProvided;

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

	public Event(LocalDate dateSubmitted, LocalTime timeSubmitted, String location, String description, double cost,
			String workJustification, int workTimeMissed, boolean requiredPresentation, int gradeCutoffId) {
		super();
		this.dateSubmitted = dateSubmitted;
		this.timeSubmitted = timeSubmitted;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.workJustification = workJustification;
		this.workTimeMissed = workTimeMissed;
		this.requiredPresentation = requiredPresentation;
		this.gradeCutoffId = gradeCutoffId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public LocalDate getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(LocalDate dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public LocalTime getTimeSubmitted() {
		return timeSubmitted;
	}

	public void setTimeSubmitted(LocalTime timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
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

	public int getGradeCutoffId() {
		return gradeCutoffId;
	}

	public void setGradeCutoffId(int gradeCutoffId) {
		this.gradeCutoffId = gradeCutoffId;
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

	public boolean isPassingGradeProvided() {
		return passingGradeProvided;
	}

	public void setPassingGradeProvided(boolean passingGradeProvided) {
		this.passingGradeProvided = passingGradeProvided;
	}

	public boolean isSuccessfulPresentationProvided() {
		return successfulPresentationProvided;
	}

	public void setSuccessfulPresentationProvided(boolean successfulPresentationProvided) {
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
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateSubmitted == null) ? 0 : dateSubmitted.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + employeeId;
		result = prime * result + eventId;
		result = prime * result + eventTypeId;
		result = prime * result + gradingFormatId;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + (requiredPresentation ? 1231 : 1237);
		result = prime * result + ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
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
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (dateSubmitted == null) {
			if (other.dateSubmitted != null)
				return false;
		} else if (!dateSubmitted.equals(other.dateSubmitted))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (eventId != other.eventId)
			return false;
		if (eventTypeId != other.eventTypeId)
			return false;
		if (gradingFormatId != other.gradingFormatId)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (requiredPresentation != other.requiredPresentation)
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
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
		return "Event [eventId=" + eventId + ", dateSubmitted=" + dateSubmitted + ", timeSubmitted=" + timeSubmitted
				+ ", location=" + location + ", description=" + description + ", cost=" + cost + ", workJustification="
				+ workJustification + ", workTimeMissed=" + workTimeMissed + ", requiredPresentation="
				+ requiredPresentation + ", gradeCutoffId=" + gradeCutoffId + ", projectedAmountReimbused="
				+ projectedAmountReimbused + ", acceptedAmountReimbursed=" + acceptedAmountReimbursed + ", isUrgent="
				+ isUrgent + ", exceedsAvaliableFunds=" + exceedsAvaliableFunds + ", passingGradeProvided="
				+ passingGradeProvided + ", successfulPresentationProvided=" + successfulPresentationProvided
				+ ", employeeId=" + employeeId + ", eventTypeId=" + eventTypeId + ", gradingFormatId=" + gradingFormatId
				+ ", dsEventStatus=" + dsEventStatus + ", hdEventStatus=" + hdEventStatus + ", bencoEventStatus="
				+ bencoEventStatus + ", approvalStage=" + approvalStage + "]";
	}

}

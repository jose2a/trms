package com.revature.trms.pojos;

import java.time.LocalDate;

public class EventStage {

	private int eventStageId;

	private LocalDate dateApproved;
	private boolean informationRequired;
	private ApprovalStage approvalStage;
	private EventStageStatus eventStageStatus;
	private int eventId;

	public EventStage() {
		super();
	}

	public EventStage(LocalDate dateApproved, boolean informationRequired, ApprovalStage approvalStage,
			EventStageStatus eventStageStatus, int eventId) {
		super();
		this.dateApproved = dateApproved;
		this.informationRequired = informationRequired;
		this.approvalStage = approvalStage;
		this.eventStageStatus = eventStageStatus;
		this.eventId = eventId;
	}

	public int getEventStageId() {
		return eventStageId;
	}

	public void setEventStageId(int eventStageId) {
		this.eventStageId = eventStageId;
	}

	public LocalDate getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(LocalDate dateApproved) {
		this.dateApproved = dateApproved;
	}

	public boolean isInformationRequired() {
		return informationRequired;
	}

	public void setInformationRequired(boolean informationRequired) {
		this.informationRequired = informationRequired;
	}

	public ApprovalStage getApprovalStage() {
		return approvalStage;
	}

	public void setApprovalStage(ApprovalStage approvalStage) {
		this.approvalStage = approvalStage;
	}

	public EventStageStatus getEventStageStatus() {
		return eventStageStatus;
	}

	public void setEventStageStatus(EventStageStatus eventStageStatus) {
		this.eventStageStatus = eventStageStatus;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStage == null) ? 0 : approvalStage.hashCode());
		result = prime * result + ((dateApproved == null) ? 0 : dateApproved.hashCode());
		result = prime * result + eventId;
		result = prime * result + eventStageId;
		result = prime * result + ((eventStageStatus == null) ? 0 : eventStageStatus.hashCode());
		result = prime * result + (informationRequired ? 1231 : 1237);
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
		EventStage other = (EventStage) obj;
		if (approvalStage != other.approvalStage)
			return false;
		if (dateApproved == null) {
			if (other.dateApproved != null)
				return false;
		} else if (!dateApproved.equals(other.dateApproved))
			return false;
		if (eventId != other.eventId)
			return false;
		if (eventStageId != other.eventStageId)
			return false;
		if (eventStageStatus != other.eventStageStatus)
			return false;
		if (informationRequired != other.informationRequired)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EventStage [eventStageId=" + eventStageId + ", dateApproved=" + dateApproved + ", informationRequired="
				+ informationRequired + ", approvalStage=" + approvalStage + ", eventStageStatus=" + eventStageStatus
				+ ", eventId=" + eventId + "]";
	}

}

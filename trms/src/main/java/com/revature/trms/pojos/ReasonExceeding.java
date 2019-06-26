package com.revature.trms.pojos;

public class ReasonExceeding {

	private int eventId;
	
	private String reason;

	public ReasonExceeding() {
		super();
	}

	public ReasonExceeding(int eventId, String reason) {
		super();
		this.eventId = eventId;
		this.reason = reason;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + eventId;
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
		ReasonExceeding other = (ReasonExceeding) obj;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (eventId != other.eventId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReasonExceeding [eventId=" + eventId + ", reason=" + reason + "]";
	}

}

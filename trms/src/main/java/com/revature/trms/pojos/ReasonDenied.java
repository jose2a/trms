package com.revature.trms.pojos;

public class ReasonDenied {

	private Integer eventId;
	
	private String reason;

	public ReasonDenied() {
		super();
	}

	public ReasonDenied(Integer eventId, String reason) {
		super();
		this.eventId = eventId;
		this.reason = reason;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
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
		ReasonDenied other = (ReasonDenied) obj;
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
		return "ReasonDenied [eventId=" + eventId + ", reason=" + reason + "]";
	}

}

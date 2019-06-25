package com.revature.trms.pojos;

public class ReasonExceeding {

	private int reasonExceedingId;
	private String reason;

	public ReasonExceeding() {
		super();
	}

	public ReasonExceeding(int reasonExceedingId, String reason) {
		super();
		this.reasonExceedingId = reasonExceedingId;
		this.reason = reason;
	}

	public int getReasonExceedingId() {
		return reasonExceedingId;
	}

	public void setReasonExceedingId(int reasonExceedingId) {
		this.reasonExceedingId = reasonExceedingId;
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
		result = prime * result + reasonExceedingId;
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
		if (reasonExceedingId != other.reasonExceedingId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReasonExceeding [reasonExceedingId=" + reasonExceedingId + ", reason=" + reason + "]";
	}

	

}

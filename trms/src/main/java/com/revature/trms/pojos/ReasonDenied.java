package com.revature.trms.pojos;

public class ReasonDenied {

	private int reasonDeniedId;
	private String reason;

	public ReasonDenied() {
		super();
	}

	public ReasonDenied(int reasonDeniedId, String reason) {
		super();
		this.reasonDeniedId = reasonDeniedId;
		this.reason = reason;
	}

	public int getReasonDeniedId() {
		return reasonDeniedId;
	}

	public void setReasonDeniedId(int reasonDeniedId) {
		this.reasonDeniedId = reasonDeniedId;
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
		result = prime * result + reasonDeniedId;
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
		if (reasonDeniedId != other.reasonDeniedId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReasonDenied [reasonDeniedId=" + reasonDeniedId + ", reason=" + reason + "]";
	}

}

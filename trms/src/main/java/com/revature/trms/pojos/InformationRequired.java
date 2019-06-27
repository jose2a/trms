package com.revature.trms.pojos;

public class InformationRequired {

	private int eventId;
	private int employeeId;

	private String information;
	private boolean provided;
	private int requiredBy;

	public InformationRequired() {
		super();
	}

	public InformationRequired(int eventId, int employeeId, String information, boolean provided, int requiredBy) {
		super();
		this.eventId = eventId;
		this.employeeId = employeeId;
		this.information = information;
		this.provided = provided;
		this.requiredBy = requiredBy;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean provided) {
		this.provided = provided;
	}

	public int getRequiredBy() {
		return requiredBy;
	}

	public void setRequiredBy(int requiredBy) {
		this.requiredBy = requiredBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
		result = prime * result + eventId;
		result = prime * result + ((information == null) ? 0 : information.hashCode());
		result = prime * result + (provided ? 1231 : 1237);
		result = prime * result + requiredBy;
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
		InformationRequired other = (InformationRequired) obj;
		if (employeeId != other.employeeId)
			return false;
		if (eventId != other.eventId)
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (provided != other.provided)
			return false;
		if (requiredBy != other.requiredBy)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InformationRequired [eventId=" + eventId + ", employeeId=" + employeeId + ", information=" + information
				+ ", provided=" + provided + ", requiredBy=" + requiredBy + "]";
	}

}

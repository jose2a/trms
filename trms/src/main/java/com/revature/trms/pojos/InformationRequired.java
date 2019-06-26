package com.revature.trms.pojos;

public class InformationRequired {

	private int eventId;
	private int employeeId;

	private String information;

	public InformationRequired() {
		super();
	}

	public InformationRequired(int eventId, int employeeId, String information) {
		super();
		this.eventId = eventId;
		this.employeeId = employeeId;
		this.information = information;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
		result = prime * result + ((information == null) ? 0 : information.hashCode());
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
		InformationRequired other = (InformationRequired) obj;
		if (employeeId != other.employeeId)
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (eventId != other.eventId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InformationRequired [stageId=" + eventId + ", employeeId=" + employeeId + ", information=" + information
				+ "]";
	}

}

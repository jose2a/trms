package com.revature.trms.pojos;

public class InformationRequired {

	private Integer eventId;
	private Integer employeeId;

	private String information;
	private boolean provided;
	private Integer requiredBy;

	private Event event;
	private Employee reuireBy;

	public InformationRequired() {
		super();
	}

	public InformationRequired(Integer eventId, Integer employeeId, String information, boolean provided,
			Integer requiredBy) {
		super();
		this.eventId = eventId;
		this.employeeId = employeeId;
		this.information = information;
		this.provided = provided;
		this.requiredBy = requiredBy;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
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

	public Integer getRequiredBy() {
		return requiredBy;
	}

	public void setRequiredBy(Integer requiredBy) {
		this.requiredBy = requiredBy;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Employee getReuireBy() {
		return reuireBy;
	}

	public void setReuireBy(Employee reuireBy) {
		this.reuireBy = reuireBy;
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

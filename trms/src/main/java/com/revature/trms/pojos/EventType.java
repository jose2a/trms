package com.revature.trms.pojos;

public class EventType {

	private int eventTypeId;
	
	private String name;
	private int reimburseCoverage;

	public EventType() {
		super();
	}

	public EventType(String name, int reimburseCoverage) {
		super();
		this.name = name;
		this.reimburseCoverage = reimburseCoverage;
	}

	public int getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReimburseCoverage() {
		return reimburseCoverage;
	}

	public void setReimburseCoverage(int reimburseCoverage) {
		this.reimburseCoverage = reimburseCoverage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventTypeId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + reimburseCoverage;
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
		EventType other = (EventType) obj;
		if (eventTypeId != other.eventTypeId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reimburseCoverage != other.reimburseCoverage)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EventType [eventTypeId=" + eventTypeId + ", name=" + name + ", reimburseCoverage=" + reimburseCoverage
				+ "]";
	}

}

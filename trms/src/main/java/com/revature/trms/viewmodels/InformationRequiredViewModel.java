package com.revature.trms.viewmodels;

import com.revature.trms.pojos.EmployeeType;

public class InformationRequiredViewModel {

	private EmployeeType requestInfoFrom;
	private Integer eventId;
	private String information;

	public EmployeeType getRequestInfoFrom() {
		return requestInfoFrom;
	}

	public void setRequestInfoFrom(EmployeeType requestInfoFrom) {
		this.requestInfoFrom = requestInfoFrom;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

}

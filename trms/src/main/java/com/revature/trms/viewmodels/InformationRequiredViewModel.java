package com.revature.trms.viewmodels;

public class InformationRequiredViewModel {

	private Integer requestInfoFrom;
	private Integer eventId;
	private String information;

	public Integer getRequestInfoFrom() {
		return requestInfoFrom;
	}

	public void setRequestInfoFrom(Integer requestInfoFrom) {
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

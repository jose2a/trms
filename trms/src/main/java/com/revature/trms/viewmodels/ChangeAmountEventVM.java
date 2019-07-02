package com.revature.trms.viewmodels;

public class ChangeAmountEventVM {

	private Integer eventId;

	private double newAmount;

	private String reason;

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public double getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(double newAmount) {
		this.newAmount = newAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}

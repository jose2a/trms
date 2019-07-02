package com.revature.trms.viewmodels;

import com.revature.trms.pojos.Event;

/**
 * Helper class to get extra grading format data when submitting the event.
 *
 */
public class EventWithGradingFmt {
	
	private Event event;
	private String from;
	private String to;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}

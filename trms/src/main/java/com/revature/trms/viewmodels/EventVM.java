package com.revature.trms.viewmodels;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.revature.trms.utilities.SqlTimeDeserializer;

public class EventVM {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfEvent;
	@JsonFormat(pattern = "HH:mm")
	@JsonDeserialize(using = SqlTimeDeserializer.class)
	private Time timeOfEvent;
	private String location;
	private String description;
	private Double cost;
	private String workJustification;
	private boolean requiredPresentation;
	private String gradeCutoff;
	private int workTimeMissed;

	private Integer eventTypeId;
	private Integer gradingFormatId;

	private String from;
	private String to;

	public Date getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(Date dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	public Time getTimeOfEvent() {
		return timeOfEvent;
	}

	public void setTimeOfEvent(Time timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getWorkJustification() {
		return workJustification;
	}

	public void setWorkJustification(String workJustification) {
		this.workJustification = workJustification;
	}

	public boolean isRequiredPresentation() {
		return requiredPresentation;
	}

	public void setRequiredPresentation(boolean requiredPresentation) {
		this.requiredPresentation = requiredPresentation;
	}

	public String getGradeCutoff() {
		return gradeCutoff;
	}

	public void setGradeCutoff(String gradeCutoff) {
		this.gradeCutoff = gradeCutoff;
	}

	public int getWorkTimeMissed() {
		return workTimeMissed;
	}

	public void setWorkTimeMissed(int workTimeMissed) {
		this.workTimeMissed = workTimeMissed;
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public Integer getGradingFormatId() {
		return gradingFormatId;
	}

	public void setGradingFormatId(Integer gradingFormatId) {
		this.gradingFormatId = gradingFormatId;
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

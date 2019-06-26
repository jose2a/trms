package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.EventType;

public interface EventTypeService {

	public EventType getEventTypeById(int eventTypeId);

	public List<EventType> getAllEventType();
}

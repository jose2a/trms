package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.EventType;

public interface EventTypeService {

	public EventType getEventTypeById(Integer eventTypeId);

	public List<EventType> getAllEventType();
}

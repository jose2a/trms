package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.EventType;

public interface EventTypeDAO {
	public boolean addEventType(EventType eventType);

	public boolean updateEventType(EventType eventType);

	public boolean deleteEventType(int eventType);

	public EventType getEventTypeById(int eventTypeId);

	public List<EventType> getAllEventType();
}

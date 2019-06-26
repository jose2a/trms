package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.EventType;

public interface EventTypeDAO {

	public EventType getEventTypeById(int eventTypeId);

	public List<EventType> getAllEventType();
}

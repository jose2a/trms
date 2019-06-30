package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EventType;

public interface EventTypeService {

	public EventType getEventTypeById(Integer eventTypeId) throws IllegalParameterException;

	public List<EventType> getAllEventType();
}

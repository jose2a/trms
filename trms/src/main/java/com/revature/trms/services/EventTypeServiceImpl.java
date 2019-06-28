package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EventTypeDAO;
import com.revature.trms.pojos.EventType;
import com.revature.trms.utilities.DAOUtilities;

public class EventTypeServiceImpl extends BaseService implements EventTypeService {

	private EventTypeDAO eventTypeDao;

	public EventTypeServiceImpl() {
		eventTypeDao = DAOUtilities.geEventTypeDAO();
	}

	@Override
	public EventType getEventTypeById(Integer eventTypeId) {
		if (eventTypeId == null) {
			throw new IllegalArgumentException("EventTypeId should not be empty.");
		}

		return eventTypeDao.getEventTypeById(eventTypeId);
	}

	@Override
	public List<EventType> getAllEventType() {
		return eventTypeDao.getAllEventType();
	}

}

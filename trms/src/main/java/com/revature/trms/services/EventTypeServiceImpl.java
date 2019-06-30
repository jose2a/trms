package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EventTypeDAO;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EventType;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class EventTypeServiceImpl extends BaseService implements EventTypeService {

	private EventTypeDAO eventTypeDao;

	public EventTypeServiceImpl() {
		eventTypeDao = DAOUtilities.geEventTypeDAO();
	}

	@Override
	public EventType getEventTypeById(Integer eventTypeId) throws IllegalParameterException {
		LogUtilities.trace("getEventTypeById");
		
		if (eventTypeId == null) {
			throw new IllegalParameterException("getEventTypeById - EventTypeId should not be empty.");
		}

		return eventTypeDao.getEventTypeById(eventTypeId);
	}

	@Override
	public List<EventType> getAllEventType() {
		LogUtilities.trace("getAllEventType");
		
		return eventTypeDao.getAllEventTypes();
	}

}

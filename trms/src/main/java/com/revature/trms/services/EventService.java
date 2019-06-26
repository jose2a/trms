package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.Event;

public interface EventService {

	public boolean addEvent(Event event);

	public boolean updateEvent(Event event);

	public boolean deleteEvent(int eventId);

	public Event getEventById(int eventId);

	public List<Event> getEventsForDirectSupervisor(Integer employeeId);

	public List<Event> getEventsForHeadDepartment(Integer employeeId);

	public List<Event> getEventsForBenefitsCoordinator(Integer employeeId);
}

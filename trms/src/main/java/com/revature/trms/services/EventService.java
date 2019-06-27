package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.Event;

public interface EventService {

	public boolean addEvent(Event event);

	public boolean updateEvent(Event event);

	public boolean deleteEvent(Integer eventId);

	public Event getEventById(Integer eventId);

	public List<Event> getEventsPendingOfDirectSupervisorApproval();

	public List<Event> getEventsPendingOfHeadDepartmentApproval();

	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval();
	
	public List<Event> getUrgentEventsPendingOfDirectSupervisorApproval();

	public List<Event> getUrgentEventsPendingOfHeadDepartmentApproval();

	public List<Event> getUrgentEventsPendingOfBenefitsCoordinatorApproval();
}

package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.Event;

public interface EventDAO {

	public boolean addEvent(Event event);

	public boolean updateEvent(Event event);

	public boolean deleteEvent(int eventId);

	public Event getEventById(int eventId);

	public List<Event> getEventsPendingOfDirectSupervisorApproval();

	public List<Event> getEventsPendingOfHeadDepartmentApproval();

	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval();

}

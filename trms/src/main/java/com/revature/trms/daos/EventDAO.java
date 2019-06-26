package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.ApprovalStage;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;

public interface EventDAO {
	
	public boolean addEvent(Event event);
	public boolean updateEvent(Event event);
	public boolean deleteEvent(int eventId);
	
	public Event getEventById(int eventId);
	
	public List<Event> getEventsEmployeeIdAndEventStatus(int employeeId, EventStatus eventStatus, ApprovalStage approvalStage);
	public List<Event> getEventsByEventStatus(EventStatus eventStatus);
//	public List<Event> getEventsByApprovalStageAndEventStageStatus(ApprovalStage approvalStage, EventStageStatus eventStageStatus);

}

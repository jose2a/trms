package com.revature.trms.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EvaluationResult;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.utilities.LogUtilities;

public class EventDAOImplTest {

	private EventDAOImpl eventDao;

	private EmployeeDAOImpl empDao;
	private EventTypeDAOImpl eventTypeDao;
	private GradingFormatDAOImpl gradingFDao;

	@Before
	public void setUp() throws Exception {
		eventDao = new EventDAOImpl();
		empDao = new EmployeeDAOImpl();
		eventTypeDao = new EventTypeDAOImpl();
		gradingFDao = new GradingFormatDAOImpl();
	}

	@Test
	public void testAddEvent() {
		GradingFormat gradingFmt = gradingFDao.getGradingFormatById(2);
		EventType eventT = eventTypeDao.getEventTypeById(2);
		Employee emp = empDao.getEmployeeById(27);

		Event event = new Event(LocalDate.now().plusWeeks(3l), LocalTime.now(), "FIU", "Python Course for beginners",
				500d, "Python is needed to perform my job.", true, "D");

		event.setPassingGradeProvided(EvaluationResult.Pending);
		event.setSuccessfulPresentationProvided(EvaluationResult.Pending);
		event.setProjectedAmountReimbused(570);
		event.setAcceptedAmountReimbursed(0);
		event.setUrgent(false);
		event.setExceedsAvaliableFunds(false);
		event.setCanceledByEmployee(false);
		event.setEventTypeId(eventT.getEventTypeId());
		event.setGradingFormatId(gradingFmt.getGradingFormatId());
		event.setEmployeeId(emp.getEmployeeId());
		event.setDsEventStatus(EventStatus.Approved);
		event.setHdEventStatus(EventStatus.Approved);
		event.setBencoEventStatus(EventStatus.Approved);

		boolean added = eventDao.addEvent(event);

		assertTrue(added);

//		eventDao.deleteEvent(event.getEventTypeId());
	}

	@Test
	public void testUpdateEvent() {
		Event event = eventDao.getEventById(1);

		event.setUrgent(true);
		event.setDsEventStatus(EventStatus.Approved);

		eventDao.updateEvent(event);
	}

	@Test
	public void testDeleteEvent() {
		boolean deleted = eventDao.deleteEvent(5);

		assertTrue(deleted);
	}

	@Test
	public void testGetEventById() {

	}

	@Test
	public void testGetEventsPendingOfDirectSupervisorApproval() {
		List<Event> events = eventDao.getEventsPendingOfDirectSupervisorApproval();

		for (Event event : events) {
			LocalDate now = LocalDate.now();
		    LocalDate sixDaysBehind = event.getDateOfEvent();
		    
		    Period period = Period.between(now, sixDaysBehind);
		    int diff = period.getDays();
		    
			LogUtilities.trace(">>>Diff " + diff);
			
			LogUtilities.trace(event.toString());
		}

		assertEquals(4, events.size());
	}

	@Test
	public void testGetEventsPendingOfHeadDepartmentApproval() {
		List<Event> events = eventDao.getEventsPendingOfHeadDepartmentApproval();

		for (Event event : events) {
			LocalDate now = LocalDate.now();
		    LocalDate sixDaysBehind = event.getDateOfEvent();
		    
		    Period period = Period.between(now, sixDaysBehind);
		    int diff = period.getDays();
		    
			LogUtilities.trace(">>>Diff " + diff);
			
			LogUtilities.trace(event.toString());
		}

		assertEquals(1, events.size());
	}

	@Test
	public void testGetEventsPendingOfBenefitsCoordinatorApproval() {
		List<Event> events = eventDao.getEventsPendingOfBenefitsCoordinatorApproval();

		for (Event event : events) {
			LocalDate now = LocalDate.now();
		    LocalDate sixDaysBehind = event.getDateOfEvent();
		    
		    Period period = Period.between(now, sixDaysBehind);
		    int diff = period.getDays();
		    
			LogUtilities.trace(">>>Diff " + diff);
			
			LogUtilities.trace(event.toString());
		}

		assertEquals(2, events.size());
	}

}

package com.revature.trms.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.trms.daos.EventDAO;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.AttachmentDocType;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.EvaluationResult;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {

	@Mock
	private EventDAO eventDao;

	@Mock
	private EmployeeService employeeService;
	@Mock
	private EventTypeService eventTypeService;
	@Mock
	private GradingFormatService gradingFormatService;
	@Mock
	private AttachmentService attachmentService;
	@Mock
	private InformationRequiredService informationRequiredService;
	@Mock
	private ReasonDeniedService reasonDeniedService;
	@Mock
	private ReasonExceedingService reasonExceedingService;

	@InjectMocks
	private EventServiceImpl eventService = new EventServiceImpl();;

	private Employee emp1; // Employee who submitted the form
	private Employee emp2; // employee
	private Employee emp3; // employee

	private Employee dsEmp; // DS
	private Employee dsAndHdEmp; // DS

	private Employee dhEmp; // DH
	private Employee benCo; // BenCo
	private Employee benCoDS; // Benco Manager

	private EventType evTCourse;
	private EventType evTSeminar;

	private GradingFormat gFPresentation;
	private GradingFormat gFtoA;

	private Attachment attPDF;
	private Attachment attDSApproval; // DS approval
	private Attachment attHDAppr; // HD approval

	private Event ev1Emp12018;
	private Event ev1Emp12019Approved;
	private Event ev2Emp12019Pending;
	private Event ev3Emp12019Denied;

	private Integer newEventId = 10;
	private Event event;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		dhEmp = new Employee("dhEmp", "s3cret", "Dep. Head", "LN", "email");
		dhEmp.setEmployeeId(1);

		benCoDS = new Employee("benCoDS", "s3cret", "Benco DS", "LN", "email");
		benCoDS.setEmployeeId(2);

		benCo = new Employee("benCo", "s3cret", "Benco", "LN", "email");
		benCo.setEmployeeId(3);
		benCo.setSupervisorId(benCoDS.getEmployeeId());

		// DS
		dsEmp = new Employee("ds1", "s3cret", "Dir. Sup. 1", "LN", "email");
		dsEmp.setEmployeeId(4);
		dsEmp.setSupervisorId(dhEmp.getEmployeeId());

		dsAndHdEmp = new Employee("dsAndHD", "s3cret", "Dir. Sup. 2", "LN", "email");
		dsAndHdEmp.setEmployeeId(5);

		// Employee
		emp1 = new Employee("emp1", "s3cret", "Employee 1", "LN 1", "email");
		emp1.setEmployeeId(6);
		emp1.setSupervisorId(dsEmp.getEmployeeId());

		emp2 = new Employee("emp2", "s3cret", "Employee 2", "LN 2", "email");
		emp2.setEmployeeId(7);
		emp2.setSupervisorId(dsEmp.getEmployeeId());

		emp3 = new Employee("emp3", "s3cret", "Employee 3", "LN 3", "email");
		emp3.setEmployeeId(8);
		emp3.setSupervisorId(dsAndHdEmp.getSupervisorId());

		evTCourse = new EventType("University Courses", 80);
		evTCourse.setEventTypeId(1);
		evTSeminar = new EventType("Seminars", 60);
		evTSeminar.setEventTypeId(2);

		gFPresentation = new GradingFormat("Pass", "Fail");
		gFPresentation.setGradingFormatId(1);

		gFtoA = new GradingFormat("A", "Z");
		gFtoA.setGradingFormatId(2);

		// Events
		// Last year
		ev1Emp12018 = new Event(Date.valueOf(LocalDate.now().minusYears(1)), Time.valueOf(LocalTime.NOON), "FIU",
				"Python Course for beginners", 500d, "Python is needed to perform my analytics job.", false, "D");
		ev1Emp12018.setEventId(1);

		ev1Emp12018.setFinalGrade("B");
		ev1Emp12018.setPresentationUploaded(false);
		ev1Emp12018.setProjectedAmountReimbused(400);
		ev1Emp12018.setAcceptedAmountReimbursed(400);
		ev1Emp12018.setUrgent(false);
		ev1Emp12018.setExceedsAvaliableFunds(false);
		ev1Emp12018.setCanceledByEmployee(false);

		ev1Emp12018.setPassingGradeProvided(EvaluationResult.Yes);
		ev1Emp12018.setSuccessfulPresentationProvided(EvaluationResult.Pending);

		ev1Emp12018.setEventTypeId(evTCourse.getEventTypeId());
		ev1Emp12018.setGradingFormatId(gFtoA.getGradingFormatId());
		ev1Emp12018.setEmployeeId(emp1.getEmployeeId());
		ev1Emp12018.setDsEventStatus(EventStatus.Approved);
		ev1Emp12018.setHdEventStatus(EventStatus.Approved);
		ev1Emp12018.setBencoEventStatus(EventStatus.Approved);
		ev1Emp12018.setReimbursementStatus(EventStatus.Approved);

		// Event this year
		// event approved
		ev1Emp12019Approved = new Event(Date.valueOf(LocalDate.now().minusMonths(1)), Time.valueOf(LocalTime.NOON),
				"USF", "Java course for beginners", 300d, "Java is requied for my project.", false, "D");
		ev1Emp12019Approved.setEventId(2);

		ev1Emp12019Approved.setFinalGrade("A");
		ev1Emp12019Approved.setPresentationUploaded(false);
		ev1Emp12019Approved.setProjectedAmountReimbused(240);
		ev1Emp12019Approved.setAcceptedAmountReimbursed(240);
		ev1Emp12019Approved.setUrgent(false);
		ev1Emp12019Approved.setExceedsAvaliableFunds(false);
		ev1Emp12019Approved.setCanceledByEmployee(false);

		ev1Emp12019Approved.setPassingGradeProvided(EvaluationResult.Yes);
		ev1Emp12019Approved.setSuccessfulPresentationProvided(EvaluationResult.Pending);

		ev1Emp12019Approved.setEventTypeId(evTCourse.getEventTypeId());
		ev1Emp12019Approved.setGradingFormatId(gFtoA.getGradingFormatId());
		ev1Emp12019Approved.setEmployeeId(emp1.getEmployeeId());
		ev1Emp12019Approved.setDsEventStatus(EventStatus.Approved);
		ev1Emp12019Approved.setHdEventStatus(EventStatus.Approved);
		ev1Emp12019Approved.setBencoEventStatus(EventStatus.Approved);

		// event pending
		ev2Emp12019Pending = new Event(Date.valueOf(LocalDate.now().plusWeeks(4)), Time.valueOf(LocalTime.NOON), "USF",
				"Java 2 course for beginners", 400d, "Java2 is requied for project improvements.", false, "D");
		ev2Emp12019Pending.setEventId(3);

		ev2Emp12019Pending.setFinalGrade("");
		ev2Emp12019Pending.setPresentationUploaded(false);
		ev2Emp12019Pending.setProjectedAmountReimbused(320);
		ev2Emp12019Pending.setAcceptedAmountReimbursed(0);
		ev2Emp12019Pending.setUrgent(false);
		ev2Emp12019Pending.setExceedsAvaliableFunds(false);
		ev2Emp12019Pending.setCanceledByEmployee(false);

		ev2Emp12019Pending.setPassingGradeProvided(EvaluationResult.Pending);
		ev2Emp12019Pending.setSuccessfulPresentationProvided(EvaluationResult.Pending);

		ev2Emp12019Pending.setEventTypeId(evTCourse.getEventTypeId());
		ev2Emp12019Pending.setGradingFormatId(gFtoA.getGradingFormatId());
		ev2Emp12019Pending.setEmployeeId(emp1.getEmployeeId());
		ev2Emp12019Pending.setDsEventStatus(EventStatus.Approved);
		ev2Emp12019Pending.setHdEventStatus(EventStatus.Pending);
		ev2Emp12019Pending.setBencoEventStatus(EventStatus.Pending);

		// Denied
		ev3Emp12019Denied = new Event(Date.valueOf(LocalDate.now().minusMonths(2)), Time.valueOf(LocalTime.NOON), "UCF",
				"Personal grow", 300d, "Personal grow is important", true, "");
		ev3Emp12019Denied.setEventId(4);

		ev3Emp12019Denied.setProjectedAmountReimbused(180);
		ev3Emp12019Denied.setPassingGradeProvided(EvaluationResult.Pending);
		ev3Emp12019Denied.setSuccessfulPresentationProvided(EvaluationResult.Pending);

		ev3Emp12019Denied.setEventTypeId(evTSeminar.getEventTypeId());
		ev3Emp12019Denied.setGradingFormatId(gFPresentation.getGradingFormatId());
		ev3Emp12019Denied.setEmployeeId(emp1.getEmployeeId());
		ev3Emp12019Denied.setDsEventStatus(EventStatus.Denied);
		ev3Emp12019Denied.setHdEventStatus(EventStatus.Pending);
		ev3Emp12019Denied.setBencoEventStatus(EventStatus.Pending);

		attPDF = new Attachment("pdf.png", Date.valueOf(LocalDate.now().plusDays(15)),
				AttachmentDocType.Direct_Supervisor_Approval, newEventId);
		attDSApproval = new Attachment("dsApp.png", Date.valueOf(LocalDate.now().plusDays(15)),
				AttachmentDocType.Direct_Supervisor_Approval, newEventId);
		attHDAppr = new Attachment("hdApp.png", Date.valueOf(LocalDate.now().plusDays(15)),
				AttachmentDocType.Department_Head_Approval, newEventId);
	}

	@Test
	public void completeTuitionReimbursementForm_InfoCorrect_ShouldReturnTrue()
			throws PojoValidationException, IllegalParameterException {
		// Setting up the data for the test
		List<Event> eventsNotDenied = new ArrayList<Event>();
		eventsNotDenied.add(ev1Emp12019Approved);
		eventsNotDenied.add(ev2Emp12019Pending);

		List<Attachment> attachments = new ArrayList<Attachment>();

		event = new Event(Date.valueOf(LocalDate.now().plusDays(12)), Time.valueOf(LocalTime.NOON), "USF",
				"Advanced Java.", 500d, "Advanced Java is needed to perform my job better.", false, "D");
		event.setEventTypeId(evTCourse.getEventTypeId());
		event.setGradingFormatId(gFPresentation.getGradingFormatId());
		event.setEmployeeId(emp1.getEmployeeId());

		// Setting up mockito
		when(eventDao.addEvent(event)).thenReturn(true);
		when(eventTypeService.getEventTypeById(1)).thenReturn(evTCourse);
		when(eventDao.getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019)).thenReturn(eventsNotDenied);

		// Calling the method
		boolean res = eventService.completeTuitionReimbursementForm(event, "", "", attachments);

		// Asserting
		verify(eventDao).addEvent(event);
		verify(eventTypeService).getEventTypeById(evTCourse.getEventTypeId());
		verify(eventDao).getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019);

		assertTrue(res);
	}

	@Test
	public void completeTuitionReimbursementForm_ProjectedAmtExceeds_EventShouldAdjust()
			throws PojoValidationException, IllegalParameterException {
		// Setting up the data for the test
		List<Event> eventsNotDenied = new ArrayList<Event>();
		eventsNotDenied.add(ev1Emp12019Approved);
		eventsNotDenied.add(ev2Emp12019Pending);

		List<Attachment> attachments = new ArrayList<Attachment>();

		event = new Event(Date.valueOf(LocalDate.now().plusDays(15)), Time.valueOf(LocalTime.NOON), "USF",
				"Advanced Java.", 600d, "Advanced Java is needed to perform my job better.", false, "D");
		event.setEventTypeId(evTCourse.getEventTypeId());
		event.setGradingFormatId(gFPresentation.getGradingFormatId());
		event.setEmployeeId(emp1.getEmployeeId());

		// Setting up mockito
		when(eventDao.addEvent(event)).thenReturn(true);
		when(eventTypeService.getEventTypeById(1)).thenReturn(evTCourse);
		when(eventDao.getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019)).thenReturn(eventsNotDenied);

		// Calling the method
		boolean res = eventService.completeTuitionReimbursementForm(event, "", "", attachments);

		// Asserting
		assertTrue(res);
		assertTrue(440 == event.getProjectedAmountReimbused());
		verify(eventDao).addEvent(event);
		verify(eventTypeService).getEventTypeById(evTCourse.getEventTypeId());
		verify(eventDao).getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019);
	}

	@Test
	public void completeTuitionReimbursementForm_GradingFormatNotProvided_ShouldCreateNewGradingFormat()
			throws Exception {
		// Setting up the data for the test
		List<Event> eventsNotDenied = new ArrayList<Event>();
		eventsNotDenied.add(ev1Emp12019Approved);
		eventsNotDenied.add(ev2Emp12019Pending);

		List<Attachment> attachments = new ArrayList<Attachment>();

		event = new Event(Date.valueOf(LocalDate.now().plusDays(15)), Time.valueOf(LocalTime.NOON), "USF",
				"Advanced Java.", 600d, "Advanced Java is needed to perform my job better.", false, "D");
		event.setEventTypeId(evTCourse.getEventTypeId());
		event.setEmployeeId(emp1.getEmployeeId());

		// Setting up mockito
		when(eventDao.addEvent(event)).thenReturn(true);
		when(eventTypeService.getEventTypeById(1)).thenReturn(evTCourse);
		when(eventDao.getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019)).thenReturn(eventsNotDenied);
		when(gradingFormatService.addGradingFormat(Mockito.any())).thenReturn(true);

		// Calling the method
		boolean res = eventService.completeTuitionReimbursementForm(event, "0", "100", attachments);

		// Asserting
		verify(eventDao).addEvent(event);
		verify(gradingFormatService).addGradingFormat(Mockito.any());
		verify(eventTypeService).getEventTypeById(evTCourse.getEventTypeId());
		verify(eventDao).getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019);

		assertTrue(res);
	}

	@Test
	public void completeTuitionReimbursementForm_DSApprovalProvided_ShouldSetsEventStatusToApproved() throws Exception {
		// Setting up the data for the test
		List<Event> eventsNotDenied = new ArrayList<Event>();
		eventsNotDenied.add(ev1Emp12019Approved);
		eventsNotDenied.add(ev2Emp12019Pending);

		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(attPDF);
		attachments.add(attDSApproval);

		event = new Event(Date.valueOf(LocalDate.now().plusDays(15)), Time.valueOf(LocalTime.NOON), "USF",
				"Advanced Java.", 600d, "Advanced Java is needed to perform my job better.", false, "D");
		event.setEventTypeId(evTCourse.getEventTypeId());
		event.setEmployeeId(emp1.getEmployeeId());
		event.setGradingFormatId(gFtoA.getGradingFormatId());

		// Setting up mockito
		when(eventDao.addEvent(event)).thenReturn(true);
		when(eventTypeService.getEventTypeById(1)).thenReturn(evTCourse);
		when(eventDao.getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019)).thenReturn(eventsNotDenied);
		when(attachmentService.addAttachment(Mockito.any())).thenReturn(true);

		// Calling the method
		boolean res = eventService.completeTuitionReimbursementForm(event, "", "", attachments);

		// Asserting
		verify(eventDao).addEvent(event);
		verify(attachmentService, times(2)).addAttachment(Mockito.any());
		verify(eventTypeService).getEventTypeById(evTCourse.getEventTypeId());
		verify(eventDao).getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019);

		assertTrue(res);
		assertTrue(EventStatus.Approved == event.getDsEventStatus());
		assertTrue(EventStatus.Pending == event.getHdEventStatus());
		assertTrue(EventStatus.Pending == event.getBencoEventStatus());
	}

	@Test
	public void completeTuitionReimbursementForm_DHApprovalProvided_ShouldSetsEventStatusToApproved() throws Exception {
		// Setting up the data for the test
		List<Event> eventsNotDenied = new ArrayList<Event>();
		eventsNotDenied.add(ev1Emp12019Approved);
		eventsNotDenied.add(ev2Emp12019Pending);

		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(attPDF);
		attachments.add(attDSApproval);
		attachments.add(attHDAppr);

		event = new Event(Date.valueOf(LocalDate.now().plusDays(15)), Time.valueOf(LocalTime.NOON), "USF",
				"Advanced Java.", 600d, "Advanced Java is needed to perform my job better.", false, "D");
		event.setEventTypeId(evTCourse.getEventTypeId());
		event.setEmployeeId(emp1.getEmployeeId());
		event.setGradingFormatId(gFtoA.getGradingFormatId());

		// Setting up mockito
		when(eventDao.addEvent(event)).thenReturn(true);
		when(eventTypeService.getEventTypeById(1)).thenReturn(evTCourse);
		when(eventDao.getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019)).thenReturn(eventsNotDenied);
		when(attachmentService.addAttachment(Mockito.any())).thenReturn(true);

		// Calling the method
		boolean res = eventService.completeTuitionReimbursementForm(event, "", "", attachments);

		// Asserting
		verify(eventDao).addEvent(event);
		verify(attachmentService, times(3)).addAttachment(Mockito.any());
		verify(eventTypeService).getEventTypeById(evTCourse.getEventTypeId());
		verify(eventDao).getEventsNotDeniedByEmployeeAndYear(emp1.getEmployeeId(), 2019);

		assertTrue(res);
		assertTrue(EventStatus.Approved == event.getDsEventStatus());
		assertTrue(EventStatus.Approved == event.getHdEventStatus());
		assertTrue(EventStatus.Pending == event.getBencoEventStatus());
	}

	@Test
	public void approveTuitionReimbursementByDirectSupervisor()
			throws NotFoundRecordException, IllegalParameterException {
		List<EmployeeType> types = new ArrayList<>();
		types.add(EmployeeType.Associate);
		types.add(EmployeeType.Direct_Supervisor);

		ev2Emp12019Pending.setDsEventStatus(EventStatus.Pending);
		dhEmp.setEmployeeTypes(types);

		// setting up mockito
		when(eventDao.getEventById(ev2Emp12019Pending.getEventId())).thenReturn(ev2Emp12019Pending);
		when(eventDao.updateEvent(ev2Emp12019Pending)).thenReturn(true);
		when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(dhEmp);

		boolean result = eventService.approveTuitionReimbursementByDirectSupervisor(ev2Emp12019Pending.getEventId(),
				emp1.getSupervisorId());

		assertTrue(result);
		assertTrue(EventStatus.Approved == ev2Emp12019Pending.getDsEventStatus());

		verify(employeeService).getEmployeeById(Mockito.anyInt());
		verify(eventDao).getEventById(ev2Emp12019Pending.getEventId());
		verify(eventDao).updateEvent(ev2Emp12019Pending);
	}

	@Test
	public void approveTuitionReimbursementByDirectSupervisor_DSisHD()
			throws NotFoundRecordException, IllegalParameterException {
		List<EmployeeType> types = new ArrayList<>();
		types.add(EmployeeType.Associate);
		types.add(EmployeeType.Direct_Supervisor);
		types.add(EmployeeType.Head_Department);

		ev2Emp12019Pending.setDsEventStatus(EventStatus.Pending);
		dsAndHdEmp.setEmployeeTypes(types);

		// setting up mockito
		when(eventDao.getEventById(ev2Emp12019Pending.getEventId())).thenReturn(ev2Emp12019Pending);
		when(eventDao.updateEvent(ev2Emp12019Pending)).thenReturn(true);
		when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(dsAndHdEmp);

		boolean result = eventService.approveTuitionReimbursementByDirectSupervisor(ev2Emp12019Pending.getEventId(),
				emp1.getSupervisorId());

		assertTrue(result);
		assertTrue(EventStatus.Approved == ev2Emp12019Pending.getDsEventStatus());
		assertTrue(EventStatus.Approved == ev2Emp12019Pending.getHdEventStatus());

		verify(employeeService).getEmployeeById(Mockito.anyInt());
		verify(eventDao).getEventById(ev2Emp12019Pending.getEventId());
		verify(eventDao).updateEvent(ev2Emp12019Pending);
	}

	@Test
	public void denyTuitionReimbursementByDirectSupervisor_EventShould() throws NotFoundRecordException,
			IllegalParameterException, PreexistingRecordException, PojoValidationException {
		List<EmployeeType> types = new ArrayList<>();
		types.add(EmployeeType.Associate);
		types.add(EmployeeType.Direct_Supervisor);

		ev2Emp12019Pending.setDsEventStatus(EventStatus.Pending);
		dhEmp.setEmployeeTypes(types);

		// setting up mockito
		when(eventDao.getEventById(ev2Emp12019Pending.getEventId())).thenReturn(ev2Emp12019Pending);
		when(eventDao.updateEvent(ev2Emp12019Pending)).thenReturn(true);
		when(reasonDeniedService.addReasonDenied(Mockito.any())).thenReturn(true);

		boolean result = eventService.denyTuitionReimbursementByDirectSupervisor(ev2Emp12019Pending.getEventId(),
				"No enough information provided to make an inform desicion.");

		assertTrue(result);
		assertTrue(EventStatus.Denied == ev2Emp12019Pending.getDsEventStatus());
		assertTrue(EventStatus.Denied == ev2Emp12019Pending.getReimbursementStatus());

		verify(reasonDeniedService).addReasonDenied(Mockito.any());
		verify(eventDao).getEventById(ev2Emp12019Pending.getEventId());
		verify(eventDao).updateEvent(ev2Emp12019Pending);
	}

	@Test
	public void requestInformationFromEmployee() throws NotFoundRecordException, PojoValidationException,
			PreexistingRecordException, IllegalParameterException {
		ev2Emp12019Pending.setDsEventStatus(EventStatus.Pending);

		// setting up mockito
		when(eventDao.getEventById(ev2Emp12019Pending.getEventId())).thenReturn(ev2Emp12019Pending);
		when(informationRequiredService.addInformationRequired(Mockito.any())).thenReturn(true);

		eventService.requestInformationFromEmployee(ev2Emp12019Pending.getEventId(), "Need more details",
				emp1.getSupervisorId());

		verify(informationRequiredService).addInformationRequired(Mockito.any());
		verify(eventDao).getEventById(ev2Emp12019Pending.getEventId());
	}

	@Test
	public void changeReimbursementAmount() throws PojoValidationException, PreexistingRecordException,
			IllegalParameterException, NotFoundRecordException {

		List<Event> events = new ArrayList<>();
		events.add(ev1Emp12019Approved);

		// setting up mockito
		when(eventDao.getEventById(ev2Emp12019Pending.getEventId())).thenReturn(ev2Emp12019Pending);
		when(eventDao.updateEvent(Mockito.any())).thenReturn(true);
		when(reasonExceedingService.getReasonExceedingByEventId(Mockito.anyInt())).thenReturn(null);
		when(reasonExceedingService.addReasonExceeding(Mockito.any())).thenReturn(true);
		when(eventDao.getEventsNotDeniedByEmployeeAndYear(Mockito.anyInt(), Mockito.anyInt())).thenReturn(events);

		eventService.changeReimbursementAmount(ev2Emp12019Pending.getEventId(), 800,
				"This would benefit the business.");

		assertTrue(ev2Emp12019Pending.getAcceptedAmountReimbursed() == 800);

		verify(eventDao).getEventById(ev2Emp12019Pending.getEventId());
		verify(eventDao).updateEvent(Mockito.any());
		verify(reasonExceedingService).getReasonExceedingByEventId(Mockito.anyInt());
		verify(reasonExceedingService).addReasonExceeding(Mockito.any());
		verify(eventDao).getEventsNotDeniedByEmployeeAndYear(Mockito.anyInt(), Mockito.anyInt());
	}

	@Test
	public void getEventsPendingOfDirectSupervisorApproval() throws PojoValidationException, IllegalParameterException {
		List<Event> events = new ArrayList<>();
		ev1Emp12018.setEmployeeId(emp2.getEmployeeId());
		events.add(ev1Emp12018);
		events.add(ev1Emp12019Approved);
		events.add(ev2Emp12019Pending);
		ev3Emp12019Denied.setEmployeeId(emp2.getEmployeeId());
		events.add(ev3Emp12019Denied);

		List<Integer> empIds = new ArrayList<>();
		empIds.add(6);

		when(eventDao.getEventsPendingOfDirectSupervisorApproval()).thenReturn(events);
		when(employeeService.getEmployeesIdsUnderSupervisorId(Mockito.anyInt())).thenReturn(empIds);

		List<Event> result = eventService.getEventsPendingOfDirectSupervisorApproval(emp1.getSupervisorId());

		assertEquals(2, result.size());

		verify(eventDao).getEventsPendingOfDirectSupervisorApproval();
		verify(employeeService).getEmployeesIdsUnderSupervisorId(Mockito.anyInt());
	}

}

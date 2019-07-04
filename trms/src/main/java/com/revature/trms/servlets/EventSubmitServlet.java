package com.revature.trms.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Event;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.viewmodels.EventVM;
import com.revature.trms.viewmodels.EventWithGradingFmt;

public class EventSubmitServlet extends BaseServlet implements DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("EventSubmitServlet - post");

		eventService = ServiceUtilities.getEventService();

		Integer employeeId = 31; // TODO: Get this from session
		
		String dateOfEventStr = request.getParameter("dateOfEvent");
		String timeOfEventStr = request.getParameter("timeOfEvent");
		String location = request.getParameter("location");
		String description = request.getParameter("description");
		String costStr = request.getParameter("cost");
		String workJustification = request.getParameter("workJustification");
		String requiredPresentationStr = request.getParameter("requiredPresentation");
		String eventTypeIdStr = request.getParameter("eventTypeId");
		String gradingFormatIdStr = request.getParameter("gradingFormatId");
		String workTimeMissedStr = request.getParameter("workTimeMissed");
		String gradeCutoff = request.getParameter("gradeCutoff");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		Date dateOfEvent = (dateOfEventStr == null || dateOfEventStr.isEmpty()) ? null : Date.valueOf(dateOfEventStr);
		Time timeOfEvent = (timeOfEventStr == null || timeOfEventStr.isEmpty()) ? null : Time.valueOf(timeOfEventStr);
		double cost = (costStr == null || costStr.isEmpty()) ? 0.0 : Double.parseDouble(costStr);
		boolean requiredPresentation = (requiredPresentationStr == null || requiredPresentationStr.isEmpty()) ? false : Boolean.parseBoolean(requiredPresentationStr);
		Integer eventTypeId = (eventTypeIdStr == null || eventTypeIdStr.isEmpty()) ? null : Integer.parseInt(eventTypeIdStr);
		Integer gradingFormatId = (gradingFormatIdStr == null || gradingFormatIdStr.isEmpty()) ? null : Integer.parseInt(gradingFormatIdStr);
		Integer workTimeMissed = (workTimeMissedStr == null || workTimeMissedStr.isEmpty()) ? null : Integer.parseInt(workTimeMissedStr);
		
		Event event = new Event();
		event.setDateOfEvent(dateOfEvent);
		event.setTimeOfEvent(timeOfEvent);
		event.setLocation(location);
		event.setDescription(description);
		event.setCost(cost);
		event.setWorkJustification(workJustification);
		event.setRequiredPresentation(requiredPresentation);
		event.setGradeCutoff(gradeCutoff);
		event.setGradingFormatId(gradingFormatId);
		event.setEventTypeId(eventTypeId);
		event.setWorkTimeMissed(workTimeMissed);
		
		event.setEmployeeId(employeeId);
		
		LogUtilities.trace(event.toString());

		try {
			eventService.completeTuitionReimbursementForm(event, from, to,
					null);
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (IllegalParameterException e) {
			LogUtilities.error(e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.getWriter().write(objectMapper.writeValueAsString(event));
	}

	// <url-pattern>/event/submit</url-pattern>
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventSubmitServlet - post");

		eventService = ServiceUtilities.getEventService();

		Integer employeeId = 31; // TODO: Get this from session

//		EventWithGradingFmt eventWithGrading = objectMapper.readValue(body, EventWithGradingFmt.class);
//		Event event = eventWithGrading.getEvent();
//		event.setEmployeeId(employeeId);
//
//		try {
//			eventService.completeTuitionReimbursementForm(event, eventWithGrading.getFrom(), eventWithGrading.getTo(),

					
		EventVM eventVM = objectMapper.readValue(body, EventVM.class);
		
		LogUtilities.trace(eventVM.toString());
		
		Event event = new Event();
		event.setDateOfEvent(eventVM.getDateOfEvent());
		event.setTimeOfEvent(eventVM.getTimeOfEvent());
		event.setLocation(eventVM.getLocation());
		event.setDescription(eventVM.getDescription());
		event.setCost(eventVM.getCost());
		event.setWorkJustification(eventVM.getWorkJustification());
		event.setRequiredPresentation(eventVM.isRequiredPresentation());
		event.setGradeCutoff(eventVM.getGradeCutoff());
		event.setGradingFormatId(eventVM.getGradingFormatId());
		event.setEventTypeId(eventVM.getEventTypeId());
		event.setWorkTimeMissed(eventVM.getWorkTimeMissed());
		
		event.setEmployeeId(employeeId);
		
		LogUtilities.trace(event.toString());

		try {
			eventService.completeTuitionReimbursementForm(event, eventVM.getFrom(), eventVM.getTo(),
					null);
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (IllegalParameterException e) {
			LogUtilities.error(e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.getWriter().write(objectMapper.writeValueAsString(eventVM));
	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}

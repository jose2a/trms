package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventWithGradingFmt;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventSubmitServlet extends BaseServlet implements DoGetMethod, DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	// <url-pattern>/event/submit</url-pattern>
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventSubmitServlet - post");

		eventService = ServiceUtilities.getEventService();
		
		Integer employeeId = 31; // Get this from session

		EventWithGradingFmt eventWithGrading = objectMapper.readValue(body, EventWithGradingFmt.class);
		Event event = eventWithGrading.getEvent();
		event.setEmployeeId(employeeId);

		try {
			eventService.completeTuitionReimbursementForm(event, eventWithGrading.getFrom(), eventWithGrading.getTo(),
					null);
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (IllegalParameterException e) {
			LogUtilities.error(e.getMessage());
		}

		String uri = getUri(request) + "/" + event.getEventId();

		response.setHeader("Location", uri);

		response.setStatus(HttpServletResponse.SC_CREATED);
		response.getWriter().write(objectMapper.writeValueAsString(eventWithGrading));
	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}

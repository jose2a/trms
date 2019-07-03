package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.Event;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventDHPendingServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/dh/pending/</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventDHPendingServlet - get");

		eventService = ServiceUtilities.getEventService();

		// TODO Remove this and use session
		Integer supervisorId = 16;

		LogUtilities.trace("SupervisorId: " + supervisorId);

		String eventsString = "";
		List<Event> events = null;

		try {
			events = eventService.getEventsPendingOfHeadDepartmentApproval(supervisorId);

		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventDSPendingServlet. " + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		eventsString = objectMapper.writeValueAsString(events);
		response.getWriter().write(eventsString);
	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}

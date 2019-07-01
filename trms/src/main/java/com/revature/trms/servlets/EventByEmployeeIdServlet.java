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

public class EventByEmployeeIdServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventByEmployeeIdServlet - get");

		eventService = ServiceUtilities.getEventService();

		if (pathInfoParts.length == 0 || pathInfoParts[1].trim().equals("")) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;
		
		Integer employeeId = Integer.parseInt(id);

		LogUtilities.trace("EmployeeId: " + employeeId);

		String eventsString = "";
		List<Event> events = null;

		try {
			events = eventService.getEventsByEmployeeId(employeeId);
			
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventByEmployeeIdServlet. " + e.getMessage());
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

package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.trms.pojos.EventType;
import com.revature.trms.services.EventTypeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventTypeServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventTypeService eventTypeService;

	// <url-pattern>/event/type/</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		eventTypeService = ServiceUtilities.getEventTypeService();

		List<EventType> eventTypes = eventTypeService.getAllEventType();

		try {
			response.getWriter().append(objectMapper.writeValueAsString(eventTypes));
		} catch (JsonProcessingException e) {
			LogUtilities.error("Error. EventTypeServlet. " + e.getMessage());
		}
	}

	@Override
	boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

}

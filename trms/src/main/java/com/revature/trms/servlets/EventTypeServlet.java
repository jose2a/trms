package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.services.EventTypeService;
import com.revature.trms.services.GradingFormatService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventTypeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventTypeService eventTypeService;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		eventTypeService = ServiceUtilities.getEventTypeService();

		ObjectMapper om = new ObjectMapper();

		List<EventType> eventTypes = eventTypeService.getAllEventType();

		String eventTypesString = "";

		try {
			eventTypesString = om.writeValueAsString(eventTypes);
		} catch (JsonProcessingException e) {
			LogUtilities.error("Error. EventTypeServlet. " + e.getMessage());
		}

		response.getWriter().append(eventTypesString);
	}

}

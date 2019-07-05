package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.Event;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventByIdServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/id/*</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventByIdServlet - get");

		eventService = ServiceUtilities.getEventService();

		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;

		if (id == null || id == "") {

			LogUtilities.error("EventByIdServlet - EventId was not provided");

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Integer eventId = Integer.parseInt(id);

		Event event = null;

		try {
			event = eventService.getEventById(eventId);
			
			// Setting employee
			Employee emp = ServiceUtilities.getEmployeeService().getEmployeeById(event.getEmployeeId());
			emp.setPassword("******");
			event.setEmployee(emp);
			
			// Setting grading format
			event.setGradingFormat(ServiceUtilities.getGradingFormatService().getGradingFormatById(event.getGradingFormatId()));
			
			// Setting event type
			event.setEventType(ServiceUtilities.getEventTypeService().getEventTypeById(event.getEventTypeId()));

			response.getWriter().write(objectMapper.writeValueAsString(event));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventByIdServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (NotFoundRecordException e) {
			LogUtilities.error("Error. EventByIdServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return employeeTypes.contains(EmployeeType.Direct_Supervisor)
				|| employeeTypes.contains(EmployeeType.Head_Department)
				|| employeeTypes.contains(EmployeeType.Benefits_Coordinator);
	}

}

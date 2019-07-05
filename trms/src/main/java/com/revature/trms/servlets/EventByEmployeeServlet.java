package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.Event;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.utilities.SessionUtilities;

public class EventByEmployeeServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/employee</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventByEmployeeIdServlet - get");

		eventService = ServiceUtilities.getEventService();
		
		Employee employee = SessionUtilities.getEmployeeFromSession(request);
		
		LogUtilities.trace("Session: " + employee.toString());

		List<Event> events = null;

		try {
			events = eventService.getEventsByEmployeeId(employee.getEmployeeId());
			
			response.getWriter().write(objectMapper.writeValueAsString(events));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventByEmployeeIdServlet. " + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return !(employeeTypes.contains(EmployeeType.Direct_Supervisor) 
				|| employeeTypes.contains(EmployeeType.Head_Department)
				|| employeeTypes.contains(EmployeeType.Benefits_Coordinator));
	}

}

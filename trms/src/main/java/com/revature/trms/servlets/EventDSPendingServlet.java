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

public class EventDSPendingServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/ds/pending</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventDSPendingServlet - get");

		eventService = ServiceUtilities.getEventService();

		Employee employee = null;
		try {
			employee = ServiceUtilities.getEmployeeService().getEmployeeById(16);
		} catch (NotFoundRecordException | IllegalParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Integer supervisorId = 16; // TODO: Getting this from session

		LogUtilities.trace("SupervisorId: " + supervisorId);

		String eventsString = "";
		List<Event> events = null;

		try {
			events = eventService.getEventsPendingOfDirectSupervisorApproval(employee.getEmployeeId());

			if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) {
				events.addAll(eventService.getEventsPendingOfHeadDepartmentApproval(employee.getEmployeeId()));
			}

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

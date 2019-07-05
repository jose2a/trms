package com.revature.trms.servlets;

import java.io.IOException;
import java.util.ArrayList;
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

public class EventPendingServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/pending</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventDSPendingServlet - get");

		eventService = ServiceUtilities.getEventService();

		Employee employee = SessionUtilities.getEmployeeFromSession(request);
		LogUtilities.trace("Session: " + employee.toString());
		LogUtilities.trace(employee.getEmployeeTypes().toString());

		List<Event> events = new ArrayList<>();

		try {
			if (employee.getEmployeeTypes().contains(EmployeeType.Direct_Supervisor)) {
				events = eventService.getEventsPendingOfDirectSupervisorApproval(employee.getEmployeeId());
				
				if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) {
					events.addAll(eventService.getEventsPendingOfHeadDepartmentApproval(employee.getEmployeeId()));
				}				
			} else if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) {
				events = eventService.getEventsPendingOfHeadDepartmentApproval(employee.getEmployeeId());
			} else if (employee.getEmployeeTypes().contains(EmployeeType.Benefits_Coordinator)) {
				events = eventService.getEventsPendingOfBenefitsCoordinatorApproval();
			}

			response.getWriter().write(objectMapper.writeValueAsString(events));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventDSPendingServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		if (employeeTypes.contains(EmployeeType.Direct_Supervisor)
				|| employeeTypes.contains(EmployeeType.Head_Department)
				|| employeeTypes.contains(EmployeeType.Benefits_Coordinator)) {
			return true;
		}
		return false;
	}

}

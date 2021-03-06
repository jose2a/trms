package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventCancelServlet extends BaseServlet implements DoPutMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/cancel/*</url-pattern>
	@Override
	public void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventCancelServlet - get");

		eventService = ServiceUtilities.getEventService();

		if (pathInfoParts.length == 0 || pathInfoParts[1].trim().equals("")) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;

		Integer eventId = Integer.parseInt(id);

		boolean isSuccess = false;

		try {
			isSuccess = eventService.cancelReimbursementRequest(eventId);

			
			if (isSuccess) {
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventCancelServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return !(employeeTypes.contains(EmployeeType.Direct_Supervisor) 
				|| employeeTypes.contains(EmployeeType.Head_Department)
				|| employeeTypes.contains(EmployeeType.Benefits_Coordinator));
	}

}

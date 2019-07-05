package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventProjectedAmtServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/projectedamt</url-pattern>
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventProjectedAmtServlet - get");
		
		eventService = ServiceUtilities.getEventService();
		
		try {
			String costStr = request.getParameter("cost");
			String eventTypeIdStr = request.getParameter("eventTypeId");
			
			LogUtilities.trace(costStr + " " + eventTypeIdStr);

			double cost = Double.parseDouble(costStr);
			Integer eventTypeId = Integer.parseInt(eventTypeIdStr);
			
			double projectedamount = eventService.getProjectedReimbursementForEvent(cost, eventTypeId);
			
			LogUtilities.trace("projected: " + projectedamount);
			
			response.getWriter().append(objectMapper.writeValueAsString(projectedamount));
			
		} catch (IllegalParameterException | NumberFormatException e) {
			LogUtilities.error("Error");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			response.getWriter().append("Cost not valid input");
		}
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}

}

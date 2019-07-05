package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.viewmodels.ChangeAmountEventVM;

public class EventChangeServlet extends BaseServlet implements DoPutMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/changeamount</url-pattern>
	@Override
	public void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventChangeServlet - post");

		eventService = ServiceUtilities.getEventService();

		ChangeAmountEventVM vm = objectMapper.readValue(body, ChangeAmountEventVM.class);

		try {
			eventService.changeReimbursementAmount(vm.getEventId(), vm.getNewAmount(), vm.getReason());
			
			LogUtilities.trace("Changed the amount for the event");
			
			return;
		} catch (NotFoundRecordException e1) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (IllegalParameterException e) {
			LogUtilities.error(e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

		} catch (PreexistingRecordException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));
		}

	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return employeeTypes.contains(EmployeeType.Benefits_Coordinator);
	}
}

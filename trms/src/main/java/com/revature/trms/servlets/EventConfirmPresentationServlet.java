package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.viewmodels.ConfirmGradePresVM;

public class EventConfirmPresentationServlet extends BaseServlet implements DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/confirm/presentation</url-pattern>
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventConfirmGradeServlet - post");

		eventService = ServiceUtilities.getEventService();

		ConfirmGradePresVM vm = objectMapper.readValue(body, ConfirmGradePresVM.class);

		try {
			eventService.confirmSuccessfulPresentation(vm.getEventId(), vm.isSuccessful());
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));
		} catch (IllegalParameterException e) {
			LogUtilities.error(e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return employeeTypes.contains(EmployeeType.Direct_Supervisor);
	}

}

package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EventDeniedServlet extends BaseServlet implements DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/deny</url-pattern>
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventDeniedServlet - post");

		eventService = ServiceUtilities.getEventService();

		// TODO: Remove later, getting from session
		Employee employee = null;
		try {
			employee = ServiceUtilities.getEmployeeService().getEmployeeById(16);
		} catch (NotFoundRecordException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ReasonDenied reasonDenied = objectMapper.readValue(body, ReasonDenied.class);

		try {
			if (employee.getEmployeeTypes().contains(EmployeeType.Direct_Supervisor)) { // Denied by DS
				eventService.denyTuitionReimbursementByDirectSupervisor(reasonDenied.getEventId(),
						reasonDenied.getReason());
				
			} else if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) { // Denied by DH
				eventService.denyTuitionReimbursementByHeadDepartment(reasonDenied.getEventId(),
						reasonDenied.getReason());
				
			} else if (employee.getEmployeeTypes().contains(EmployeeType.Benefits_Coordinator)) { // Denied by Benco
				eventService.denyTuitionReimbursementByBenCo(reasonDenied.getEventId(), reasonDenied.getReason());
			}
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventDeniedServlet. " + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (PreexistingRecordException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));

			return;
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

//		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}
}

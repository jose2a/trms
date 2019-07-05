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
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.utilities.SessionUtilities;

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

		Employee employee = SessionUtilities.getEmployeeFromSession(request);
		
		LogUtilities.trace("Session: " + employee);

		ReasonDenied reasonDenied = objectMapper.readValue(body, ReasonDenied.class);

		try {
			if (employee.getEmployeeTypes().contains(EmployeeType.Direct_Supervisor)) { // Denied by DS
				LogUtilities.trace("Denied by Direct Supervisor");
				
				eventService.denyTuitionReimbursementByDirectSupervisor(reasonDenied.getEventId(),
						reasonDenied.getReason());
				
			} else if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) { // Denied by DH
				LogUtilities.trace("Denied by Head Department");
				
				eventService.denyTuitionReimbursementByHeadDepartment(reasonDenied.getEventId(),
						reasonDenied.getReason());
				
			} else if (employee.getEmployeeTypes().contains(EmployeeType.Benefits_Coordinator)) { // Denied by Benco
				LogUtilities.trace("Denied by Benco");
				eventService.denyTuitionReimbursementByBenCo(reasonDenied.getEventId(), reasonDenied.getReason());
			}
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventDeniedServlet. " + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));
		} catch (PreexistingRecordException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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

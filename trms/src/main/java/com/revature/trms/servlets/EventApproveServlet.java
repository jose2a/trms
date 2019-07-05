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
import com.revature.trms.utilities.SessionUtilities;
import com.revature.trms.viewmodels.ApproveEventVM;

public class EventApproveServlet extends BaseServlet implements DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/approve</url-pattern>
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventApproveServlet - put");

		eventService = ServiceUtilities.getEventService();

		Employee employee = SessionUtilities.getEmployeeFromSession(request);

		LogUtilities.trace("Session: " + employee.toString());

		ApproveEventVM vm = objectMapper.readValue(body, ApproveEventVM.class);

		try {
			Event event = eventService.getEventById(vm.getEventId());

			if (employee.getEmployeeTypes().contains(EmployeeType.Direct_Supervisor)) { // Denied by DS
				LogUtilities.trace("Approved by Direct Supervisor");

				eventService.approveTuitionReimbursementByDirectSupervisor(event.getEventId(),
						employee.getEmployeeId());

			} else if (employee.getEmployeeTypes().contains(EmployeeType.Head_Department)) { // Denied by DH
				LogUtilities.trace("Approved by Head Department");

				eventService.approveTuitionReimbursementByHeadDepartment(event.getEventId());

			} else if (employee.getEmployeeTypes().contains(EmployeeType.Benefits_Coordinator)) { // Denied by Benco
				LogUtilities.trace("Approved by Benco");
				eventService.approveTuitionReimbursementByBenCo(event.getEventId());
			}
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EventApproveServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

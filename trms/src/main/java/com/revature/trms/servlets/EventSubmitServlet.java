package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.Event;
import com.revature.trms.services.EventService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.utilities.SessionUtilities;
import com.revature.trms.viewmodels.EventVM;

public class EventSubmitServlet extends BaseServlet implements DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EventService eventService;

	// <url-pattern>/event/submit</url-pattern>
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("EventSubmitServlet - post");

		eventService = ServiceUtilities.getEventService();

		Employee employee = SessionUtilities.getEmployeeFromSession(request);
		
		LogUtilities.info("Session: " + employee.toString());

		EventVM vm = objectMapper.readValue(body, EventVM.class);

		LogUtilities.trace(vm.toString());

		Event event = new Event();
		event.setDateOfEvent(vm.getDateOfEvent());
		event.setTimeOfEvent(vm.getTimeOfEvent());
		event.setLocation(vm.getLocation());
		event.setDescription(vm.getDescription());
		event.setCost(vm.getCost());
		event.setWorkJustification(vm.getWorkJustification());
		event.setRequiredPresentation(vm.isRequiredPresentation());
		event.setGradeCutoff(vm.getGradeCutoff());
		event.setGradingFormatId(vm.getGradingFormatId());
		event.setEventTypeId(vm.getEventTypeId());
		event.setWorkTimeMissed(vm.getWorkTimeMissed());

		event.setEmployeeId(employee.getEmployeeId());

		LogUtilities.trace(event.toString());

		try {
			eventService.completeTuitionReimbursementForm(event, vm.getFrom(), vm.getTo(), null);

			response.getWriter().write(objectMapper.writeValueAsString(event));
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));
		} catch (IllegalParameterException e) {
			LogUtilities.error(e.getMessage());

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

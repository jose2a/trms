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
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.services.EventService;
import com.revature.trms.services.InformationRequiredService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.utilities.SessionUtilities;
import com.revature.trms.viewmodels.InformationRequiredViewModel;

public class InfoRequiredServlet extends BaseServlet implements DoGetMethod, DoPutMethod, DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private InformationRequiredService infoReqService;
	private EventService eventService;

	// <url-pattern>/inforeq</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - get");

		infoReqService = ServiceUtilities.getInformationRequiredService();

		List<InformationRequired> infoRequiredList = null;

		Employee employee = SessionUtilities.getEmployeeFromSession(request);

		LogUtilities.info("Session: " + employee.toString());

		try {
			infoRequiredList = infoReqService.getInformationRequiredByEmployeeId(employee.getEmployeeId());

			response.getWriter().append(objectMapper.writeValueAsString(infoRequiredList));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - post");

		infoReqService = ServiceUtilities.getInformationRequiredService();
		eventService = ServiceUtilities.getEventService();

		// Get employee from session
		Employee employee = SessionUtilities.getEmployeeFromSession(request);

		LogUtilities.info("Session: " + employee.toString());

		// Create a helper class to get data from post
		InformationRequiredViewModel vm = objectMapper.readValue(body, InformationRequiredViewModel.class);

		EmployeeType eType = EmployeeType.valueOf(vm.getRequestInfoFrom());

		LogUtilities.trace("Info from: " + eType);

		try {
			if (eType == EmployeeType.Associate) { // Employee
				eventService.requestInformationFromEmployee(vm.getEventId(), vm.getInformation(),
						employee.getEmployeeId());
			} else if (eType == EmployeeType.Direct_Supervisor) {
				eventService.requestInformationFromDirectSupervisor(vm.getEventId(), vm.getInformation(),
						employee.getEmployeeId());
			} else if (eType == EmployeeType.Head_Department) {
				eventService.requestInformationFromDepartmentHead(vm.getEventId(), vm.getInformation(),
						employee.getEmployeeId());
			}
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

		} catch (PreexistingRecordException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - put");

		eventService = ServiceUtilities.getEventService();

		InformationRequired informationRequired = objectMapper.readValue(body, InformationRequired.class);
		
		Employee employee = SessionUtilities.getEmployeeFromSession(request);
		LogUtilities.info("Session: " + employee.toString());

		try {
			eventService.confirmSentOfInformationRequired(informationRequired.getEventId(), employee.getEmployeeId());
			response.getWriter().append(objectMapper.writeValueAsString(informationRequired));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}
}

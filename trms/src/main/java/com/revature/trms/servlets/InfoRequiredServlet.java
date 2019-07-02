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
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeServiceImpl;
import com.revature.trms.services.EventService;
import com.revature.trms.services.InformationRequiredService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.viewmodels.InformationRequiredViewModel;

public class InfoRequiredServlet extends BaseServlet implements DoGetMethod, DoPutMethod, DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private InformationRequiredService infoReqService;
	private EventService eventService;
	private EmployeeService employeeServ = new EmployeeServiceImpl(); // To be remove

	// <url-pattern>/inforeq</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - get");

		infoReqService = ServiceUtilities.getInformationRequiredService();

		List<InformationRequired> infoRequiredList = null;

		int employeeId = 25; // TODO: Remove it, get this from Session

		try {
			infoRequiredList = infoReqService.getInformationRequiredByEmployeeId(employeeId);
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.getWriter().append(objectMapper.writeValueAsString(infoRequiredList));
	}

	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - post");

		infoReqService = ServiceUtilities.getInformationRequiredService();
		eventService = ServiceUtilities.getEventService();

		// Get employee from session
		Employee employee = null; // TODO: Remove it, get this from Session
		try {
			employee = employeeServ.getEmployeeById(16);
		} catch (NotFoundRecordException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Create a helper class to get data from post
		InformationRequiredViewModel informationRequiredVM = objectMapper.readValue(body,
				InformationRequiredViewModel.class);

		try {
			if (informationRequiredVM.getRequestInfoFrom() == EmployeeType.Associate) { // Employee
				eventService.requestInformationFromEmployee(informationRequiredVM.getEventId(),
						informationRequiredVM.getInformation(), employee.getEmployeeId());
			} else if (informationRequiredVM.getRequestInfoFrom() == EmployeeType.Direct_Supervisor) {
				eventService.requestInformationFromDirectSupervisor(informationRequiredVM.getEventId(),
						informationRequiredVM.getInformation(), employee.getEmployeeId());
			} else if (informationRequiredVM.getRequestInfoFrom() == EmployeeType.Head_Department) {
				eventService.requestInformationFromDepartmentHead(informationRequiredVM.getEventId(),
						informationRequiredVM.getInformation(), employee.getEmployeeId());
			}
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));

			return;
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (PreexistingRecordException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));

			return;
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	@Override
	public void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - put");

		eventService = ServiceUtilities.getEventService();

		InformationRequired informationRequired = objectMapper.readValue(body, InformationRequired.class);
		int employeeId = 25; // TODO: Remove it, get this from Session

		try {
			eventService.confirmSentOfInformationRequired(informationRequired.getEventId(), employeeId);
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().append(objectMapper.writeValueAsString(informationRequired));

	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}
}

package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.services.InformationRequiredService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class InfoRequiredServlet extends BaseServlet implements DoGetMethod, DoPutMethod, DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private InformationRequiredService infoReqService;

	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - get");

		infoReqService = ServiceUtilities.getInformationRequiredService();

		List<InformationRequired> infoRequiredList = null;

		int employeeId = 24;

		try {
			infoRequiredList = infoReqService.getInformationRequiredByEmployeeId(employeeId);
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());
		}

		response.getWriter().append(objectMapper.writeValueAsString(infoRequiredList));
	}

	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - post");

		infoReqService = ServiceUtilities.getInformationRequiredService();

		InformationRequired informationRequired = objectMapper.readValue(body, InformationRequired.class);

		try {
			infoReqService.addInformationRequired(informationRequired);
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

			return;
		} catch (PreexistingRecordException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getMessage()));

			return;
		}

		response.setStatus(HttpServletResponse.SC_CREATED);
		response.getWriter().append(objectMapper.writeValueAsString(informationRequired));
	}

	@Override
	public void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtilities.trace("InfoRequiredServlet - put");

		infoReqService = ServiceUtilities.getInformationRequiredService();

		InformationRequired informationRequired = objectMapper.readValue(body, InformationRequired.class);

		try {
			infoReqService.updateInformationRequired(informationRequired);
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. InfoRequiredServlet. " + e.getMessage());
		} catch (PojoValidationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

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

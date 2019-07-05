package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.services.GradingFormatService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class GradingFormatServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private GradingFormatService gradingFmtService;

	// <url-pattern>/gradingformat/*</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		gradingFmtService = ServiceUtilities.getGradingFormatService();

		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;

		if (id == null || id == "") {

			List<GradingFormat> gradingFormats = gradingFmtService.getAllGradingFormats();

			try {
				response.getWriter().append(objectMapper.writeValueAsString(gradingFormats));
				
				return;
			} catch (JsonProcessingException e) {
				LogUtilities.error("Error. GradingFormatServlet. " + e.getMessage());

				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				
				return;
			}
		}

		Integer gradeId = Integer.parseInt(id);

		LogUtilities.trace("GradingFormatId: " + gradeId);

		GradingFormat gradingFormat = null;

		try {
			gradingFormat = gradingFmtService.getGradingFormatById(gradeId);

			if (gradingFormat == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			response.getWriter().write(objectMapper.writeValueAsString(gradingFormat));
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. GradingFormatServlet. " + e.getMessage());

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}


	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}

}

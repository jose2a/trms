package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.services.GradingFormatService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class GradingFormatServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private GradingFormatService gradingFmtService;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		gradingFmtService = ServiceUtilities.getGradingFormatService();

		ObjectMapper om = new ObjectMapper();

		String gradingId = request.getPathInfo();

		if (gradingId == null || gradingId.substring(1) == "") {

			List<GradingFormat> gradingFormats = gradingFmtService.getAllGradingFormats();

			String gradingFormatsString = "";

			try {
				gradingFormatsString = om.writeValueAsString(gradingFormats);
			} catch (JsonProcessingException e) {
				LogUtilities.error("Error. GradingFormatServlet. " + e.getMessage());
			}

			response.getWriter().append(gradingFormatsString);
			return;
		}

		Integer id = Integer.parseInt(gradingId.substring(1));
		
		LogUtilities.trace("GradingFormatId: " + id);

		String gradingFormatString = "";
		GradingFormat gradingFormat = null;

		try {
			gradingFormat = gradingFmtService.getGradingFormatById(id);

			if (gradingFormat == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. GradingFormatServlet. " + e.getMessage());
		}

		gradingFormatString = om.writeValueAsString(gradingFormat);
		response.getWriter().write(gradingFormatString);
	}

}

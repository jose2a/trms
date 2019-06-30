package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.services.GradingFormatService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class GradingFormatServlet extends BaseServlet implements DoGetMethod, DoPostMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private GradingFormatService gradingFmtService;

	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		gradingFmtService = ServiceUtilities.getGradingFormatService();

		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;

		if (id == null || id == "") {

			List<GradingFormat> gradingFormats = gradingFmtService.getAllGradingFormats();

			String gradingFormatsString = "";

			try {
				gradingFormatsString = objectMapper.writeValueAsString(gradingFormats);
			} catch (JsonProcessingException e) {
				LogUtilities.error("Error. GradingFormatServlet. " + e.getMessage());
			}

			response.getWriter().append(gradingFormatsString);
			return;
		}

		Integer gradeId = Integer.parseInt(id);

		LogUtilities.trace("GradingFormatId: " + gradeId);

		String gradingFormatString = "";
		GradingFormat gradingFormat = null;

		try {
			gradingFormat = gradingFmtService.getGradingFormatById(gradeId);

			if (gradingFormat == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. GradingFormatServlet. " + e.getMessage());
		}

		gradingFormatString = objectMapper.writeValueAsString(gradingFormat);
		response.getWriter().write(gradingFormatString);
	}

	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GradingFormat gradingFormat = objectMapper.readValue(body, GradingFormat.class);
		gradingFormat.setGradingFormatId(7);

		String uri = getUri(request) + "/" + gradingFormat.getGradingFormatId();

		response.setHeader("Location", uri);
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.getWriter().write(objectMapper.writeValueAsString(gradingFormat));
	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}

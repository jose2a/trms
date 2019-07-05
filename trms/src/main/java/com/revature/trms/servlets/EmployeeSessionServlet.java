package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.SessionUtilities;

public class EmployeeSessionServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	// <url-pattern>/employeeinfo</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogUtilities.trace("EmployeeSessionServlet");

		String employeString = objectMapper.writeValueAsString(SessionUtilities.getEmployeeFromSession(request));
		response.getWriter().write(employeString);
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}

}

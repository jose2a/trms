package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.SessionUtilities;

public class LogoutServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7752998924402336356L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LogUtilities.trace("Logging out the user");
		SessionUtilities.logout(req);
	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}

}

package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;
import com.revature.trms.utilities.SessionUtilities;

public class LoginServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9176769999044428691L;

	private EmployeeService employeeService;
	private ObjectMapper objectMapper;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LogUtilities.trace("LoginServlet");

		setJsonResponseHeaders(resp);

		employeeService = ServiceUtilities.getEmployeeService();

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		Employee employee = null;

		LogUtilities.trace("Username " + username + " - " + password);

		try {
			employee = employeeService.getEmployeeByUsernameAndPassword(username, password);

			if (employee == null) {
				LogUtilities.trace("Employee not found");
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("Username or password not valid.");

				return;
			}

			SessionUtilities.saveEmployeeToSession(req, employee);

			employee.setPassword("*******");

			resp.getWriter().append(objectMapper.writeValueAsString(employee));
		} catch (PojoValidationException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().append(objectMapper.writeValueAsString(e.getErrors()));

		} catch (IllegalParameterException e) {
			LogUtilities.error("LoginServlet. " + e.getMessage());

			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	boolean validateAuthorization(List<EmployeeType> employeeTypes) {
		return true;
	}

}

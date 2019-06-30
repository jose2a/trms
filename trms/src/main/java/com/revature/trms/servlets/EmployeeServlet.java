package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeTypeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EmployeeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EmployeeService employeeService;
	private EmployeeTypeService employeeTypeService;

	// <url-pattern>/employee/*</url-pattern>
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		employeeService = ServiceUtilities.getEmployeeService();
		employeeTypeService = ServiceUtilities.geEmployeeTypeService();

		ObjectMapper om = new ObjectMapper();

		String employeeId = request.getPathInfo();

		Integer id = Integer.parseInt(employeeId.substring(1));

		LogUtilities.trace("EmployeeId: " + id);

		String employeString = "";
		Employee employee = null;

		try {
			employee = employeeService.getEmployeeById(id);
			employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(id));
			
			LogUtilities.trace(employee.toString());
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EmployeeServlet. " + e.getMessage());
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		employeString = om.writeValueAsString(employee);
		response.getWriter().write(employeString);
	}

}

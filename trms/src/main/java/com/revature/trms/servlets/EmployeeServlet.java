package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeTypeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EmployeeServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EmployeeService employeeService;
	private EmployeeTypeService employeeTypeService;

	// <url-pattern>/employee/*</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		employeeService = ServiceUtilities.getEmployeeService();
		employeeTypeService = ServiceUtilities.geEmployeeTypeService();

		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;

		Integer employeeId = Integer.parseInt(id);

		LogUtilities.trace("EmployeeId: " + employeeId);

		String employeString = "";
		Employee employee = null;

		try {
			employee = employeeService.getEmployeeById(employeeId);
			employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employeeId));

			LogUtilities.trace(employee.toString());
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EmployeeServlet. " + e.getMessage());
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		employeString = objectMapper.writeValueAsString(employee);
		response.getWriter().write(employeString);
	}

	@Override
	public boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}

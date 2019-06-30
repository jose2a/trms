package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeTypeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EmployeeTypeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EmployeeService employeeService;
	private EmployeeTypeService employeeTypeService;

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

		String employeTypesString = "";
		List<EmployeeType> employeeTypes = null;

		try {
			employeeService.getEmployeeById(id);
			employeeTypes = employeeTypeService.getEmployeeTypesForEmployee(id);
			
			LogUtilities.trace(employeeTypes.toString());
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EmployeeTypeServlet. " + e.getMessage());
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		employeTypesString = om.writeValueAsString(employeeTypes);
		response.getWriter().write(employeTypesString);
	}

}

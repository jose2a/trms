package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeTypeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EmployeeTypeServlet extends BaseServlet implements DoGetMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	private EmployeeService employeeService;
	private EmployeeTypeService employeeTypeService;

	// <url-pattern>/role/*</url-pattern>
	@Override
	public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		employeeService = ServiceUtilities.getEmployeeService();
		employeeTypeService = ServiceUtilities.geEmployeeTypeService();

		String id = (pathInfoParts.length > 0) ? pathInfoParts[1] : null;

		Integer employeeId = Integer.parseInt(id);

		LogUtilities.trace("EmployeeId: " + employeeId);

		String employeTypesString = "";
		List<EmployeeType> employeeTypes = null;

		try {
			employeeService.getEmployeeById(employeeId);
			employeeTypes = employeeTypeService.getEmployeeTypesForEmployee(employeeId);

			LogUtilities.trace(employeeTypes.toString());
		} catch (IllegalParameterException e) {
			LogUtilities.error("Error. EmployeeTypeServlet. " + e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		} catch (NotFoundRecordException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		employeTypesString = objectMapper.writeValueAsString(employeeTypes);
		response.getWriter().write(employeTypesString);
	}

	@Override
	boolean validateAuthorization(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

}

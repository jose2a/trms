package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.services.EmployeeService;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

/**
 * Servlet implementation class ListEmployeeServlet
 */
public class ListEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeService employeeService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		employeeService = ServiceUtilities.getEmployeeService();
	
		LogUtilities.trace("List employees sevlets");

		request.setAttribute("employeeList", employeeService.getAllEmployees());

		request.getRequestDispatcher("listEmployee.jsp").forward(request, response);
	}

}

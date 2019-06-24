package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.services.EmployeeService;
import com.revature.trms.services.EmployeeServiceImpl;
import com.revature.trms.utilities.LogUtilities;

/**
 * Servlet implementation class AddEmployeeServlet
 */
public class AddEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeService employeeService;

	public AddEmployeeServlet() {
		employeeService = new EmployeeServiceImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("pageHeader", "Employee");
		request.setAttribute("pageSubHeader", "Add employee");
		
		request.setAttribute("employeeTypes", EmployeeType.values());
		
		request.getRequestDispatcher("addEmployee.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");

		Employee employee = new Employee(username, password, firstName, lastName);

		boolean isAdded = employeeService.addEmployee(employee);

		if (!isAdded) {
			LogUtilities.trace("Employee not added.");
			request.getRequestDispatcher("addEmployee.jsp").forward(request, response);
		} else {
			LogUtilities.trace("Employee added successfully.");
		}

	}

}

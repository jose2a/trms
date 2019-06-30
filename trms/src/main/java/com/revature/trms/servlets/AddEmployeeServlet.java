package com.revature.trms.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
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

		setPageAttributes(request);

		request.getRequestDispatcher("addEmployee.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Getting parameters from the request
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		String supervisorId = request.getParameter("supervisorid");

		// Parsing employeetype ids from string to integers
		String[] employeeTypes = request.getParameterValues("employeeTypes");
		List<EmployeeType> employeeType = new ArrayList<>();

		if (employeeTypes != null) {
			for (String empType : employeeTypes) {
				LogUtilities.trace("Assigning project type " + empType + " to employe.");

				employeeType.add(EmployeeType.valueOf(Integer.parseInt(empType)));
			}
		}

		// Cresting new employee
		Employee employee = new Employee(username, password, firstName, lastName, "email");
		employee.setSupervisorId(Integer.parseInt(supervisorId));
		employee.setEmployeeTypes(employeeType);

		boolean isAdded = false;

		try {
			isAdded = employeeService.addEmployee(employee);

		} catch (PojoValidationException pve) {
			// If we have validation errors, we show them to the user
			request.setAttribute("valErrors", pve.getErrors());
			isAdded = false;
		} catch (PreexistingRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isAdded) {
			LogUtilities.trace("Employee added successfully. Redirecting to employees list.");
			
			request.getSession().setAttribute("message", "Employee added successfully.");
			request.getSession().setAttribute("messageType", "success");
			
			response.sendRedirect(request.getContextPath() + "/admin/ListEmployee");
		} else {

			LogUtilities.trace("Employee not added.");
			
			request.getSession().setAttribute("message", "Employee was not added.");
			request.getSession().setAttribute("messageType", "error");

			setPageAttributes(request);
			request.getRequestDispatcher("addEmployee.jsp").forward(request, response);
		}
	}

	// Initializing list values
	private void setPageAttributes(HttpServletRequest request) {
		request.setAttribute("pageHeader", "Employee");
		request.setAttribute("pageSubHeader", "Add employee");

		request.setAttribute("supervisors", employeeService.getAllSupervisors());
		request.setAttribute("employeeTypeList", EmployeeType.values());
	}

}

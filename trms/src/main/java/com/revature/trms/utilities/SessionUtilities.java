package com.revature.trms.utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.trms.pojos.Employee;

public class SessionUtilities {

	private static final String EMPLOYEE_KEY = "employee";

	public static boolean isEmployeeInSession(HttpServletRequest request) {
		HttpSession sess = request.getSession(false);
		if (sess == null || sess.getAttribute(EMPLOYEE_KEY) == null) {
			return false;
		}

		return true;
	}

	public static Employee getEmployeeFromSession(HttpServletRequest request) {
		Employee employee = null;
		if (isEmployeeInSession(request)) {
			HttpSession session = request.getSession();

			employee = (Employee) session.getAttribute(EMPLOYEE_KEY);
		}

		return employee;
	}

	public static void saveEmployeeToSession(HttpServletRequest request, Employee employee) {
		HttpSession session = request.getSession(true);
		session.setAttribute(EMPLOYEE_KEY, employee);
	}
	
	public static void logout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}

package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.pojos.Employee;
import com.revature.trms.utilities.DAOUtilities;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDAO employeeDao;

	public EmployeeServiceImpl() {
		employeeDao = DAOUtilities.getEmployeeDAO();
	}

	@Override
	public boolean addEmployee(Employee employee) {
		boolean res = employeeDao.addEmployee(employee);
		return res;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployeeByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee loginEmployee(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

}

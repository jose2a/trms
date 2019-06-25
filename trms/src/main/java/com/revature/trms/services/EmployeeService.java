package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;

public interface EmployeeService {
	public boolean addEmployee(Employee employee) throws PojoValidationException;

	public boolean updateEmployee(Employee employee);

	public boolean deleteEmployee(int employeeId);

	public Employee getEmployeeById(int employeeId);
	
	public Employee getEmployeeByUsername(String username);

	public Employee loginEmployee(String username, String password);

	public List<Employee> getAllEmployees();
	
	public List<Employee> getAllSupervisors();
}

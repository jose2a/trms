package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.Employee;

public interface EmployeeDAO {
	public boolean addEmployee(Employee employee);

	public boolean updateEmployee(Employee employee);

	public boolean deleteEmployee(int employeeId);

	public Employee getEmployeeById(int employeeId);

	public Employee getEmployeeByUsernameAndPassword(String username, String password);

	public List<Employee> getAllEmployees();

}
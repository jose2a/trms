package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Employee;

public interface EmployeeService {
	public boolean addEmployee(Employee employee) throws PojoValidationException;

	public boolean updateEmployee(Employee employee) throws PreexistingRecordException, PojoValidationException;

	public boolean deleteEmployee(Integer employeeId) throws NotFoundRecordException;

	public Employee getEmployeeById(Integer employeeId);

	public Employee getEmployeeByUsername(String username) throws PojoValidationException;

	public Employee getEmployeeByUsernameAndPassword(String username, String password) throws PojoValidationException;
	
	public Employee getEmployeeSupervisor(Integer employeeId);

	public List<Employee> getAllEmployees();

	public List<Employee> getAllSupervisors();

	public List<Employee> getEmployeesUnderSupervisorId(Integer supervisorId) throws PojoValidationException;

	public List<Integer> getEmployeesIdsUnderSupervisorId(Integer supervisorId) throws PojoValidationException;
	
	public List<Integer> getEmployeesUnderSupervisorIdList(List<Integer> employeeListIds);
}

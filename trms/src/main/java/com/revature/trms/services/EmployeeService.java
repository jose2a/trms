package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Employee;

public interface EmployeeService {
	public boolean addEmployee(Employee employee)
			throws PojoValidationException, PreexistingRecordException, IllegalParameterException;

	public boolean updateEmployee(Employee employee) throws PojoValidationException, IllegalParameterException;

	public boolean deleteEmployee(Integer employeeId) throws NotFoundRecordException, IllegalParameterException;

	public Employee getEmployeeById(Integer employeeId) throws NotFoundRecordException, IllegalParameterException;

	public Employee getEmployeeByUsername(String username)
			throws PojoValidationException, NotFoundRecordException, IllegalParameterException;

	public Employee getEmployeeByUsernameAndPassword(String username, String password)
			throws PojoValidationException, IllegalParameterException;

	public Employee getEmployeeSupervisor(Integer employeeId) throws IllegalParameterException;

	public List<Employee> getAllEmployees();

	public List<Employee> getAllSupervisors();

	public List<Employee> getEmployeesUnderSupervisorId(Integer supervisorId)
			throws PojoValidationException, IllegalParameterException;

	public List<Integer> getEmployeesIdsUnderSupervisorId(Integer supervisorId)
			throws PojoValidationException, IllegalParameterException;

	public List<Integer> getEmployeesUnderSupervisorIdList(List<Integer> employeeListIds);
}

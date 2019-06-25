package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.daos.EmployeeTypeDAO;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class EmployeeServiceImpl extends BaseService implements EmployeeService {

	private EmployeeDAO employeeDao;
	private EmployeeTypeDAO employeeTypeDao;

	public EmployeeServiceImpl() {
		employeeDao = DAOUtilities.getEmployeeDAO();
		employeeTypeDao = DAOUtilities.getEmployeeTypeDAO();
	}

	@Override
	public boolean addEmployee(Employee employee) throws PojoValidationException {
		validateEmployee(employee);

		if (pojoValidationException.getErrors().size() > 0) {
			LogUtilities.trace("Add employee. Validation errors.");

			throw pojoValidationException;
		}

		setDefaultEmployeeType(employee);

		boolean empAdded = employeeDao.addEmployee(employee);

		if (empAdded) {
			LogUtilities.trace("Employee added successfully. Adding employee types to the employee.");

			return employeeTypeDao.addEmployeeTypesToEmployee(employee.getEmployeeId(), employee.getEmployeeTypes());
		}

		return false;
	}

	@Override
	public boolean updateEmployee(Employee employee) throws PreexistingRecordException, PojoValidationException {
		if (employeeDao.getEmployeeByUsername(employee.getUsername()) != null) {
			LogUtilities.trace("An employee with same username is in the db");

			throw new PreexistingRecordException("An employee with the same username was already added to the system.");
		}

		validateEmployee(employee);

		if (pojoValidationException.getErrors().size() > 0) {
			LogUtilities.trace("Validation errors while updating the employee");

			throw pojoValidationException;
		}

		boolean empUpdated = employeeDao.updateEmployee(employee);

		if (empUpdated) {
			employeeTypeDao.removeEmployeeTypesFromEmployee(employee.getEmployeeId());
			return employeeTypeDao.addEmployeeTypesToEmployee(employee.getEmployeeId(), employee.getEmployeeTypes());
		}

		return false;
	}

	@Override
	public boolean deleteEmployee(int employeeId) throws NotFoundRecordException {

		if (employeeDao.getEmployeeById(employeeId) == null) {
			LogUtilities.trace("Employee not found");

			throw new NotFoundRecordException("The employee was not found.");
		}

		return employeeDao.deleteEmployee(employeeId);
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		Employee employee = employeeDao.getEmployeeById(employeeId);

		if (employee != null) { // Loading employees types if the employee was found
			employee.setEmployeeTypes(employeeTypeDao.getEmployeeTypesForEmployee(employeeId));
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsername(String username) throws PojoValidationException {
		validateEmployeeUsername(username); // Validate username
		checkValidationResults(); // Check validation status

		Employee employee = employeeDao.getEmployeeByUsername(username);

		if (employee != null) { // Loading employees types if the employee was found
			employee.setEmployeeTypes(employeeTypeDao.getEmployeeTypesForEmployee(employee.getEmployeeId()));
		}

		return employee;
	}

	@Override
	public Employee loginEmployee(String username, String password) throws PojoValidationException {
		validateEmployeeUsername(username);
		validateEmployeePassword(password);
		checkValidationResults(); // Check validation status

		Employee employee = employeeDao.getEmployeeByUsernameAndPassword(username, password);
		
		if (employee != null) { // Loading employees types if the employee was found
			employee.setEmployeeTypes(employeeTypeDao.getEmployeeTypesForEmployee(employee.getEmployeeId()));
		}
		
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Override
	public List<Employee> getAllSupervisors() {
		return employeeDao.getAllSupervisors();
	}

	private void setDefaultEmployeeType(Employee employee) {
		if (!employee.getEmployeeTypes().contains(EmployeeType.Associate)) {
			employee.addEmployeeTypeId(EmployeeType.Associate);
		}
	}
}

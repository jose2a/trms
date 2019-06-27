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
		validateEmployee(employee); // validating
		checkValidationResults(); // check validation results

		LogUtilities.trace("Not validation errors while adding a new employee.");

		employee.setAvaliableReimbursementAmount(1000);
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

		validateEmployee(employee); // validating employee
		checkValidationResults(); // check validation results

		LogUtilities.trace("Not Validation errors while updating the employee");

		boolean empUpdated = employeeDao.updateEmployee(employee);

		if (empUpdated) {
			LogUtilities.trace("Employee updated.");

			employeeTypeDao.removeEmployeeTypesFromEmployee(employee.getEmployeeId());
			return employeeTypeDao.addEmployeeTypesToEmployee(employee.getEmployeeId(), employee.getEmployeeTypes());
		}

		return false;
	}

	@Override
	public boolean deleteEmployee(Integer employeeId) throws NotFoundRecordException {

		if (employeeDao.getEmployeeById(employeeId) == null) {
			LogUtilities.trace("Employee not found");

			throw new NotFoundRecordException("The employee was not found.");
		}

		return employeeDao.deleteEmployee(employeeId);
	}

	@Override
	public Employee getEmployeeById(Integer employeeId) {
		Employee employee = employeeDao.getEmployeeById(employeeId);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Loading employee types");

			employee.setEmployeeTypes(employeeTypeDao.getEmployeeTypesForEmployee(employeeId));
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsername(String username) throws PojoValidationException {
		validateEmployeeUsername(username, true); // Validate username
		checkValidationResults(); // Check validation status

		Employee employee = employeeDao.getEmployeeByUsername(username);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Loading employee types.");

			employee.setEmployeeTypes(employeeTypeDao.getEmployeeTypesForEmployee(employee.getEmployeeId()));
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsernameAndPassword(String username, String password) throws PojoValidationException {
		validateEmployeeUsername(username, true);
		validateEmployeePassword(password);
		checkValidationResults(); // Check validation results

		Employee employee = employeeDao.getEmployeeByUsernameAndPassword(username, password);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Getting employee types.");

			employee.setEmployeeTypes(employeeTypeDao.getEmployeeTypesForEmployee(employee.getEmployeeId()));
		}

		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		LogUtilities.trace("Getting all the employees");

		return employeeDao.getAllEmployees();
	}

	@Override
	public List<Employee> getAllSupervisors() {
		LogUtilities.trace("Getting all the supervisors");

		return employeeDao.getAllSupervisors();
	}

	@Override
	public List<Employee> getEmployeesUnderSupervisorId(Integer supervisorId) throws PojoValidationException {
		LogUtilities.trace("Getting all employees for this supervisor " + supervisorId);

		validateSupervisorId(supervisorId, true);
		checkValidationResults(); // check validation results

		return employeeDao.getEmployeesUnderSupervisorId(supervisorId);
	}

	@Override
	public List<Integer> getEmployeesIdsUnderSupervisorId(Integer supervisorId) throws PojoValidationException {
		LogUtilities.trace("Getting employee ids for this supervisor " + supervisorId);

		validateSupervisorId(supervisorId, true);
		checkValidationResults(); // check validation results

		return employeeDao.getEmployeesIdsUnderSupervisorId(supervisorId);

	}

	// Setting associate as a default employee type
	private void setDefaultEmployeeType(Employee employee) {
		if (!employee.getEmployeeTypes().contains(EmployeeType.Associate)) {
			employee.addEmployeeTypeId(EmployeeType.Associate);
		}
	}

	@Override
	public Employee getEmployeeSupervisor(Integer employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getEmployeesUnderSupervisorIdList(List<Integer> employeeListIds) {
		// TODO Auto-generated method stub
		return null;
	}
}

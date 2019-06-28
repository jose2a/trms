package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ServiceUtilities;

public class EmployeeServiceImpl extends BaseService implements EmployeeService {

	private EmployeeDAO employeeDao;
	private EmployeeTypeService employeeTypeService;

	public EmployeeServiceImpl() {
		employeeDao = DAOUtilities.getEmployeeDAO();
		employeeTypeService = ServiceUtilities.geEmployeeTypeService();
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

			return employeeTypeService.addEmployeeTypesToEmployee(employee.getEmployeeId(),
					employee.getEmployeeTypes());
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

			return employeeTypeService.updateEmployeeTypesToEmployee(employee.getEmployeeId(),
					employee.getEmployeeTypes());
		}

		return false;
	}

	@Override
	public boolean deleteEmployee(Integer employeeId) throws NotFoundRecordException {

		if (employeeDao.getEmployeeById(employeeId) == null) {
			LogUtilities.trace("Employee not found");

			throw new NotFoundRecordException("The employee was not found.");
		}

		boolean removed = employeeTypeService.removeEmployeeTypesFromEmployee(employeeId);

		if (removed) {
			return employeeDao.deleteEmployee(employeeId);
		}

		return false;
	}

	@Override
	public Employee getEmployeeById(Integer employeeId) {
		Employee employee = employeeDao.getEmployeeById(employeeId);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Loading employee types");

			employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employeeId));
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsername(String username) throws PojoValidationException {
		validateOnlyEmployeeUsername(username); // Validate username
		checkValidationResults(); // Check validation status

		Employee employee = employeeDao.getEmployeeByUsername(username);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Loading employee types.");

			employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employee.getEmployeeId()));
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsernameAndPassword(String username, String password) throws PojoValidationException {
		validateOnlyEmployeeUsername(username);
		validateOnlyEmployeePassword(password);
		checkValidationResults(); // Check validation results

		Employee employee = employeeDao.getEmployeeByUsernameAndPassword(username, password);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Getting employee types.");

			employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employee.getEmployeeId()));
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

		validateOnlySupervisorId(supervisorId);
		checkValidationResults(); // check validation results

		return employeeDao.getEmployeesUnderSupervisorId(supervisorId);
	}

	@Override
	public List<Integer> getEmployeesIdsUnderSupervisorId(Integer supervisorId) throws PojoValidationException {
		LogUtilities.trace("Getting employee ids for this supervisor " + supervisorId);

		validateOnlySupervisorId(supervisorId);
		checkValidationResults(); // check validation results

		return employeeDao.getEmployeesIdsUnderSupervisorId(supervisorId);

	}

	@Override
	public Employee getEmployeeSupervisor(Integer employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}

		return employeeDao.getEmployeeSupervisor(employeeId);
	}

	@Override
	public List<Integer> getEmployeesUnderSupervisorIdList(List<Integer> employeeListIds) {
		return employeeDao.getEmployeesUnderSupervisorIdList(employeeListIds);
	}

	/**
	 * Setting associate as a default employee type.
	 * 
	 * @param employee The employee
	 */
	private void setDefaultEmployeeType(Employee employee) {
		if (!employee.getEmployeeTypes().contains(EmployeeType.Associate)) {
			employee.addEmployeeTypeId(EmployeeType.Associate);
		}
	}
}

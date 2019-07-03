package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.exceptions.IllegalParameterException;
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
	public boolean addEmployee(Employee employee)
			throws PojoValidationException, PreexistingRecordException, IllegalParameterException {
		LogUtilities.trace("addEmployee");

		pojoValException = PojoValidationException.getInstance();
		validateEmployee(employee, pojoValException); // validating
		checkValidationResults(pojoValException); // check validation results

		if (employeeDao.getEmployeeByUsername(employee.getUsername()) != null) {
			throw new PreexistingRecordException("An employee with the same username already exist.");
		}

		setDefaultEmployeeType(employee);

		boolean empAdded = employeeDao.addEmployee(employee);

		if (empAdded) {
			LogUtilities.trace("Employee added successfully.");

			return employeeTypeService.addEmployeeTypesToEmployee(employee.getEmployeeId(),
					employee.getEmployeeTypes());
		}

		return false;
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

	@Override
	public boolean updateEmployee(Employee employee) throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("updateEmployee");

		pojoValException = PojoValidationException.getInstance();
		validateEmployee(employee, pojoValException); // validating employee
		checkValidationResults(pojoValException); // check validation results

		boolean empUpdated = employeeDao.updateEmployee(employee);

		if (empUpdated) {
			LogUtilities.trace("Employee updated.");

			return employeeTypeService.updateEmployeeTypesToEmployee(employee.getEmployeeId(),
					employee.getEmployeeTypes());
		}

		return false;
	}

	@Override
	public boolean deleteEmployee(Integer employeeId) throws NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("deleteEmployee");

		if (employeeId == null) {
			throw new IllegalParameterException("deleteEmployee - employeeId should not be empty");
		}

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
	public Employee getEmployeeById(Integer employeeId) throws NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("getEmployeeById");

		if (employeeId == null) {
			throw new IllegalParameterException("getEmployeeById - employeeId should not be empty");
		}

		Employee employee = employeeDao.getEmployeeById(employeeId);

		if (employee == null) {
			throw new NotFoundRecordException("Employee not found.");
		}

		LogUtilities.trace("Employee found. Loading employee types.");

		employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employeeId));

		return employee;
	}

	@Override
	public Employee getEmployeeByUsername(String username)
			throws PojoValidationException, NotFoundRecordException, IllegalParameterException {
		LogUtilities.trace("getEmployeeByUsername");

		pojoValException = PojoValidationException.getInstance();
		validateEmployeeUsername(username, pojoValException);
		checkValidationResults(pojoValException); // Check validation status

		Employee employee = employeeDao.getEmployeeByUsername(username);

		if (employee == null) {
			throw new NotFoundRecordException("Employee not found.");
		}

		LogUtilities.trace("Employee found. Loading employee types.");

		employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employee.getEmployeeId()));

		return employee;
	}

	@Override
	public Employee getEmployeeByUsernameAndPassword(String username, String password)
			throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("getEmployeeByUsernameAndPassword");

		pojoValException = PojoValidationException.getInstance();
		validateEmployeeUsername(username, pojoValException);
		validateEmployeePassword(password, pojoValException);
		checkValidationResults(pojoValException); // Check validation results

		Employee employee = employeeDao.getEmployeeByUsernameAndPassword(username, password);

		if (employee != null) { // Loading employees types if the employee was found
			LogUtilities.trace("Employee found. Getting employee types.");

			employee.setEmployeeTypes(employeeTypeService.getEmployeeTypesForEmployee(employee.getEmployeeId()));
		}

		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		LogUtilities.trace("getAllEmployees");

		return employeeDao.getAllEmployees();
	}

	@Override
	public List<Employee> getAllSupervisors() {
		LogUtilities.trace("getAllSupervisors");

		return employeeDao.getAllSupervisors();
	}

	@Override
	public List<Employee> getEmployeesUnderSupervisorId(Integer supervisorId)
			throws PojoValidationException, IllegalParameterException {
		LogUtilities.trace("getEmployeesUnderSupervisorId");

		if (supervisorId == null) {
			throw new IllegalParameterException("getEmployeesUnderSupervisorId - supervisorId should not be empty");
		}

		pojoValException = PojoValidationException.getInstance();
		validateSupervisorId(supervisorId, pojoValException);
		checkValidationResults(pojoValException); // check validation results

		return employeeDao.getEmployeesUnderSupervisorId(supervisorId);
	}

	@Override
	public List<Integer> getEmployeesIdsUnderSupervisorId(Integer supervisorId)
			throws IllegalParameterException {
		LogUtilities.trace("getEmployeesIdsUnderSupervisorId");

		if (supervisorId == null) {
			throw new IllegalParameterException("getEmployeesIdsUnderSupervisorId - supervisorId should not be empty");
		}

		return employeeDao.getEmployeesIdsUnderSupervisorId(supervisorId);

	}

	@Override
	public Employee getEmployeeSupervisor(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("getEmployeeSupervisor");

		if (employeeId == null) {
			throw new IllegalParameterException("getEmployeeSupervisor - employeeId should not be empty");
		}

		return employeeDao.getEmployeeSupervisor(employeeId);
	}

	@Override
	public List<Integer> getEmployeesUnderSupervisorIdList(List<Integer> employeeListIds) {
		LogUtilities.trace("getEmployeesUnderSupervisorIdList");

		return employeeDao.getEmployeesUnderSupervisorIdList(employeeListIds);
	}

}

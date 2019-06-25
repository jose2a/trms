package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeDAO;
import com.revature.trms.daos.EmployeeTypeDAO;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.DAOUtilities;

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

		if (pojoValExcep.getErrors().size() > 0) {
			throw pojoValExcep;
		}

		setDefaultEmployeeType(employee);

		boolean empAdded = employeeDao.addEmployee(employee);

		if (empAdded) {
			return employeeTypeDao.addEmployeeTypesToEmployee(employee.getEmployeeId(), employee.getEmployeeTypes());
		}

		return false;
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

	@Override
	public List<Employee> getAllSupervisors() {
		return employeeDao.getAllSupervisors();
	}

	private void setDefaultEmployeeType(Employee employee) {
		if (!employee.getEmployeeTypes().contains(EmployeeType.Associate.getValue())) {
			employee.addEmployeeTypeId(EmployeeType.Associate.getValue());
		}
	}
}

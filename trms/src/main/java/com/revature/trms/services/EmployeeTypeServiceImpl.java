package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeTypeDAO;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.DAOUtilities;

public class EmployeeTypeServiceImpl extends BaseService implements EmployeeTypeService {

	private EmployeeTypeDAO employeeTypeDao;

	public EmployeeTypeServiceImpl() {
		employeeTypeDao = DAOUtilities.getEmployeeTypeDAO();
	}

	@Override
	public boolean addEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds) {
		return employeeTypeDao.addEmployeeTypesToEmployee(employeeId, employeeTypeIds);
	}

	@Override
	public boolean removeEmployeeTypesFromEmployee(Integer employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}
		
		return employeeTypeDao.removeEmployeeTypesFromEmployee(employeeId);
	}

	@Override
	public List<EmployeeType> getEmployeeTypesForEmployee(Integer employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("EmployeeId should not be empty.");
		}
		
		return employeeTypeDao.getEmployeeTypesForEmployee(employeeId);
	}

	@Override
	public boolean updateEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds) {
		if (employeeTypeDao.removeEmployeeTypesFromEmployee(employeeId)) {
			return employeeTypeDao.addEmployeeTypesToEmployee(employeeId, employeeTypeIds);
		}
		
		return false;
	}

}

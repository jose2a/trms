package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.EmployeeTypeDAO;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class EmployeeTypeServiceImpl extends BaseService implements EmployeeTypeService {

	private EmployeeTypeDAO employeeTypeDao;

	public EmployeeTypeServiceImpl() {
		employeeTypeDao = DAOUtilities.getEmployeeTypeDAO();
	}

	@Override
	public boolean addEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds)
			throws IllegalParameterException {
		LogUtilities.trace("addEmployeeTypesToEmployee");

		if (employeeId == null) {
			throw new IllegalParameterException("addEmployeeTypesToEmployee - employeeId should not be empty");
		}

		return employeeTypeDao.addEmployeeTypesToEmployee(employeeId, employeeTypeIds);
	}

	@Override
	public boolean removeEmployeeTypesFromEmployee(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("removeEmployeeTypesFromEmployee");

		if (employeeId == null) {
			throw new IllegalParameterException("removeEmployeeTypesFromEmployee - employeeId should not be empty");
		}

		return employeeTypeDao.removeEmployeeTypesFromEmployee(employeeId);
	}

	@Override
	public List<EmployeeType> getEmployeeTypesForEmployee(Integer employeeId) throws IllegalParameterException {
		LogUtilities.trace("getEmployeeTypesForEmployee");

		if (employeeId == null) {
			throw new IllegalParameterException("getEmployeeTypesForEmployee - employeeId should not be empty");
		}

		return employeeTypeDao.getEmployeeTypesForEmployee(employeeId);
	}

	@Override
	public boolean updateEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds) throws IllegalParameterException {
		LogUtilities.trace("updateEmployeeTypesToEmployee");

		if (employeeId == null) {
			throw new IllegalParameterException("updateEmployeeTypesToEmployee - employeeId should not be empty");
		}
		
		if (employeeTypeDao.removeEmployeeTypesFromEmployee(employeeId)) {
			return employeeTypeDao.addEmployeeTypesToEmployee(employeeId, employeeTypeIds);
		}

		return false;
	}

}

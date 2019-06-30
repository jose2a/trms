package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.pojos.EmployeeType;

public interface EmployeeTypeService {
	public boolean addEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds) throws IllegalParameterException;
	
	public boolean updateEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds)
			throws IllegalParameterException;

	public boolean removeEmployeeTypesFromEmployee(Integer employeeId) throws IllegalParameterException;

	public List<EmployeeType> getEmployeeTypesForEmployee(Integer employeeId) throws IllegalParameterException;
}

package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.EmployeeType;

public interface EmployeeTypeService {
	public boolean addEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds);
	
	public boolean updateEmployeeTypesToEmployee(Integer employeeId, List<EmployeeType> employeeTypeIds);

	public boolean removeEmployeeTypesFromEmployee(Integer employeeId);

	public List<EmployeeType> getEmployeeTypesForEmployee(Integer employeeId);
}

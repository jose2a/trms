package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.EmployeeType;

public interface EmployeeTypeDAO {
	public boolean addEmployeeTypesToEmployee(int employeeId, List<EmployeeType> employeeTypeIds);

	public boolean removeEmployeeTypesFromEmployee(int employeeId);

	public List<EmployeeType> getEmployeeTypesForEmployee(int employeeId);
}

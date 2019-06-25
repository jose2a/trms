package com.revature.trms.daos;

import java.util.List;

public interface EmployeeTypeDAO {
	public boolean addEmployeeTypesToEmployee(int employeeId, List<Integer> employeeTypeIds);

	public boolean removeEmployeeTypesFromEmployee(int employeeId);
	
	public List<Integer> getEmployeeTypesForEmployee(int employeeId);
}

package com.revature.trms.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.pojos.Employee;

public class ModelMapperUtilities {

	public static void mapRsToEmployee(ResultSet rs, Employee employee) throws SQLException {
		employee.setEmployeeId(rs.getInt("employee_id"));
		employee.setUsername(rs.getString("username"));
		employee.setFirstName(rs.getString("first_name"));
		employee.setLastName(rs.getString("last_name"));
		employee.setSupervisorId(rs.getInt("supervisor_id"));
	}
}

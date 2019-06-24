package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.revature.trms.pojos.Employee;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public boolean addEmployee(Employee employee) {

		PreparedStatement stmt = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO Employee (username, password, firstName, lastName, idSupervisor) "
					+ "VALUES(?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, employee.getUsername());
			stmt.setString(2, employee.getPassword());
			stmt.setString(3, employee.getFirstName());
			stmt.setString(4, employee.getLastName());
			stmt.setInt(5, employee.getSupervisorId());

			if (stmt.executeUpdate() != 0)
				return true;
		} catch (Exception e) {
			LogUtilities.error("Error adding the employee." + e.getMessage());
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
	public Employee getEmployeeByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;

public class EmployeeTypeDAOImpl extends BaseDAO implements EmployeeTypeDAO {

	@Override
	public boolean addEmployeeTypesToEmployee(int employeeId, List<EmployeeType> employeeType) {
		LogUtilities.trace("Adding roles to employee. " + employeeId);

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "INSERT INTO employee_employee_type(employee_id, employee_type_id) VALUES (?, ?)";

			for (EmployeeType type : employeeType) {

				ps = conn.prepareStatement(sql);

				ps.setInt(1, employeeId);
				ps.setInt(2, type.getValue());

				if (ps.executeUpdate() == 0)
					return false;

				LogUtilities.trace("Role " + type.getValue() + " added to employee.");
			}
		} catch (SQLException e) {
			LogUtilities.error("Error adding role to employee." + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return true;
	}

	@Override
	public boolean removeEmployeeTypesFromEmployee(int employeeId) {
		LogUtilities.trace("Removing Roles from employee." + employeeId);

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "DELETE FROM employee_employee_type WHERE employee_id = ?";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, employeeId);

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Removed roles from employee.");

				return true;
			}

		} catch (SQLException e) {
			LogUtilities.error("Error removing roles from employee." + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public List<EmployeeType> getEmployeeTypesForEmployee(int employeeId) {
		LogUtilities.trace("Get roles for employee " + employeeId);

		List<EmployeeType> empTypes = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT employee_type_id FROM employee_employee_type WHERE employee_id = ?";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				empTypes.add(EmployeeType.valueOf(rs.getInt("employee_type_id")));
			}

		} catch (SQLException e) {

		} finally {
			closeResources(null, ps, null);
		}

		return empTypes;
	}

}

package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class EmployeeDAOImpl extends BaseDAO implements EmployeeDAO {

	@Override
	public boolean addEmployee(Employee employee) {
		LogUtilities.trace("Inserting employee.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO employee(username, password, first_name, last_name, supervisor_id) "
					+ "VALUES (?, ?, ?, ?, ?)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, employee.getUsername());
			ps.setString(2, employee.getPassword());
			ps.setString(3, employee.getFirstName());
			ps.setString(4, employee.getLastName());
			ps.setInt(5, employee.getSupervisorId());

			if (ps.executeUpdate() != 0) {
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					LogUtilities.trace("Getting id for employee");

					employee.setEmployeeId(rs.getInt(1));
				}

				LogUtilities.trace("Employee inserted Id: " + employee.getEmployeeId().toString());
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error adding the employee." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		LogUtilities.trace("Updating employee: " + employee.getEmployeeId().toString());

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "UPDATE employee " + "SET username=?, password=?, first_name=?, last_name=?, supervisor_id=?"
					+ "WHERE employee_id=?";

			ps = conn.prepareStatement(sql);

			ps.setString(1, employee.getUsername());
			ps.setString(2, employee.getPassword());
			ps.setString(3, employee.getFirstName());
			ps.setString(4, employee.getLastName());
			ps.setInt(5, employee.getSupervisorId());
			ps.setInt(6, employee.getEmployeeId());

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Employee updated.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error updating the employee." + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public boolean deleteEmployee(int employeeId) {
		LogUtilities.trace("Deleting employee: " + employeeId);

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "DELETE FROM employee WHERE employee_id=?";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, employeeId);

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Employee deleted.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error deleting the employee." + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		LogUtilities.trace("Getting employee by employeeId " + employeeId);

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT employee_id, username, first_name, last_name, supervisor_id FROM employee WHERE employee_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeId);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting employee by id." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsername(String username) {
		LogUtilities.trace("Getting employee by username " + username);

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT employee_id, username, first_name, last_name, supervisor_id FROM employee WHERE username = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting employee by username." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsernameAndPassword(String username, String password) {
		LogUtilities.trace("Getting employee by username and password.");

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT employee_id, username, first_name, last_name, supervisor_id FROM employee WHERE employee_id=? AND password=?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting employee by username and password." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		LogUtilities.trace("Getting all the employees.");

		List<Employee> employees = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT employee_id, username, first_name, last_name, supervisor_id FROM employee";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);

				employees.add(employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting all employees." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employees;
	}

	@Override
	public List<Employee> getAllSupervisors() {
		LogUtilities.trace("Getting employees that are supervisors.");

		List<Employee> supervisors = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT e.employee_id, username, first_name, last_name, supervisor_id "
					+ "FROM employee e inner join employee_employee_type et " + "On e.employee_id = et.employee_id "
					+ "WHERE et.employee_type_id IN (?, ?)";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, EmployeeType.Manager.getValue());
			ps.setInt(2, EmployeeType.Head_Department.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);

				supervisors.add(employee);

				LogUtilities.trace(employee.getFirstName());
			}

		} catch (SQLException e) {
			LogUtilities.error("Error getting employees that are supervisors." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return supervisors;
	}

}

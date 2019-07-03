package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class EmployeeDAOImpl extends BaseDAO implements EmployeeDAO {

	private String baseSql = "SELECT employee_id, username, password, first_name, last_name, supervisor_id, email FROM employee e";

	@Override
	public boolean addEmployee(Employee employee) {
		LogUtilities.trace("addEmployee.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO employee(username, password, first_name, last_name, email, supervisor_id)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			mapEmployeeToRS(employee, ps);

			if (ps.executeUpdate() != 0) {
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					LogUtilities.trace("Getting id for employee");

					employee.setEmployeeId(rs.getInt(1));
				}

				LogUtilities.trace("Employee inserted Id: " + employee.getEmployeeId());
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. addEmployee. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		LogUtilities.trace("updateEmployee. " + employee.getEmployeeId());

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "UPDATE employee SET username=?, password=?, first_name=?, last_name=?, email=?, supervisor_id=?"
					+ "WHERE employee_id=?";

			ps = conn.prepareStatement(sql);

			mapEmployeeToRS(employee, ps);
			ps.setInt(6, employee.getEmployeeId());

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Employee updated.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. updateEmployee. " + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	private void mapEmployeeToRS(Employee employee, PreparedStatement ps) throws SQLException {
		ps.setString(1, employee.getUsername());
		ps.setString(2, employee.getPassword());
		ps.setString(3, employee.getFirstName());
		ps.setString(4, employee.getLastName());
		ps.setString(5, employee.getEmail());
		ps.setInt(6, employee.getSupervisorId());
	}

	@Override
	public boolean deleteEmployee(int employeeId) {
		LogUtilities.trace("deleteEmployee. " + employeeId);

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
			LogUtilities.error("deleteEmployee. " + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		LogUtilities.trace("getEmployeeById. " + employeeId);

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE employee_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeId);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("getEmployeeById. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsername(String username) {
		LogUtilities.trace("getEmployeeByUsername. " + username);

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE username=?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEmployeeByUsername. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public Employee getEmployeeByUsernameAndPassword(String username, String password) {
		LogUtilities.trace("getEmployeeByUsernameAndPassword.");

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE username=? AND password=?";
			
			LogUtilities.trace(sql);

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
				
				LogUtilities.trace("Emp: " + employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEmployeeByUsernameAndPassword. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		LogUtilities.trace("getAllEmployees.");

		List<Employee> employees = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			ps = conn.prepareStatement(baseSql);

			rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);

				employees.add(employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getAllEmployees. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employees;
	}

	@Override
	public List<Employee> getAllSupervisors() {
		LogUtilities.trace("getAllSupervisors.");

		Set<Employee> supervisors = new HashSet<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT e.employee_id, username, first_name, last_name, email, password, supervisor_id"
					+ " FROM employee e inner join employee_employee_type et On e.employee_id = et.employee_id"
					+ " WHERE et.employee_type_id IN (?, ?)";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, EmployeeType.Direct_Supervisor.getValue());
			ps.setInt(2, EmployeeType.Head_Department.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);

				supervisors.add(employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getAllSupervisors. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return supervisors.stream().collect(Collectors.toList());
	}

	@Override
	public List<Employee> getEmployeesUnderSupervisorId(int supervisorId) {
		LogUtilities.trace("getEmployeesUnderSupervisorId. " + supervisorId);

		List<Employee> employees = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = baseSql + " WHERE e.supervisor_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, supervisorId);

			rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);

				employees.add(employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEmployeesUnderSupervisorId. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employees;
	}

	@Override
	public List<Integer> getEmployeesIdsUnderSupervisorId(int supervisorId) {
		LogUtilities.trace("getEmployeesIdsUnderSupervisorId. " + supervisorId);

		List<Integer> employeeIds = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT employee_id FROM employee e WHERE e.supervisor_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, supervisorId);

			rs = ps.executeQuery();

			while (rs.next()) {

				employeeIds.add(rs.getInt("employee_id"));
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEmployeesIdsUnderSupervisorId. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employeeIds;
	}

	@Override
	public Employee getEmployeeSupervisor(Integer employeeId) {
		LogUtilities.trace("getEmployeeSupervisor. " + employeeId);

		Employee employee = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = "SELECT e2.employee_id, e2.username, e2.first_name, e2.last_name, e2.password, e2.email, e2.supervisor_id"
					+ " FROM employee e1 INNER JOIN employee e2 ON e1.supervisor_id = e2.employee_id"
					+ " WHERE e1.employee_id = ?";
			
			LogUtilities.trace("SQL: " + sql);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeId);

			rs = ps.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				ModelMapperUtilities.mapRsToEmployee(rs, employee);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEmployeeSupervisor. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employee;
	}

	@Override
	public List<Integer> getEmployeesUnderSupervisorIdList(List<Integer> employeeListIds) {
		LogUtilities.trace("getEmployeesUnderSupervisorIdList.");

		List<Integer> employeeIds = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String placeHolder = getQuestionsMarksStringForSQLPlaceholder(employeeListIds.size());

			String sql = "select e1.employee_id from employee e1 where e1.supervisor_id in (" + placeHolder + ")";

			LogUtilities.trace("SQL: " + sql);

			ps = conn.prepareStatement(sql);

			int i = 0;

			for (Integer empId : employeeListIds) {
				ps.setInt(++i, empId);
			}

			rs = ps.executeQuery();

			while (rs.next()) {

				employeeIds.add(rs.getInt("employee_id"));
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEmployeesUnderSupervisorIdList. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return employeeIds;
	}

}

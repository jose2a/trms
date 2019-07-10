package com.revature.trms.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.trms.pojos.Employee;
import com.revature.trms.utilities.LogUtilities;

public class EmployeeDAOImplTest {
	private EmployeeDAOImpl empDao;

	@Before
	public void setUp() throws Exception {
		empDao = new EmployeeDAOImpl();
	}

//	@Test
	public void testGetEmployeeById() {
		int employeeId = 27;

		Employee e = empDao.getEmployeeById(employeeId);

		assertEquals("Kyla", e.getFirstName());
	}

//	@Test
	public void testGetEmployeeByUsername() {
		String username = "lionel.r";

		Employee e = empDao.getEmployeeByUsername(username);

		assertEquals((Integer) 29, e.getEmployeeId());
	}

//	@Test
	public void testGetEmployeeByUsernameAndPassword() {
		String username = "lionel.r";
		String password = "s3cret";

		Employee e = empDao.getEmployeeByUsernameAndPassword(username, password);

		LogUtilities.trace(e.toString());

		assertNotNull(e);
	}

//	@Test
	public void testGetAllSupervisors() {
		List<Employee> supervisors = empDao.getAllSupervisors();

		LogUtilities.trace(supervisors.toString());

		assertEquals(12, supervisors.size());
	}

//	@Test
	public void testGetEmployeesUnderSupervisorId() {
		List<Employee> subordinates = empDao.getEmployeesUnderSupervisorId(20);

		LogUtilities.trace(subordinates.toString());

		assertEquals(2, subordinates.size());

		subordinates = empDao.getEmployeesUnderSupervisorId(22);

		LogUtilities.trace(subordinates.toString());

		assertEquals(3, subordinates.size());
	}

//	@Test
	public void testGetEmployeesIdsUnderSupervisorId() {
		List<Integer> subordinateIds = empDao.getEmployeesIdsUnderSupervisorId(20);

		LogUtilities.trace(subordinateIds.toString());

		assertEquals(2, subordinateIds.size());

		subordinateIds = empDao.getEmployeesIdsUnderSupervisorId(22);

		LogUtilities.trace(subordinateIds.toString());

		assertEquals(3, subordinateIds.size());
		
	}

//	@Test
	public void testGetEmployeeSupervisor() {
		int employeeId = 34;

		Employee e = empDao.getEmployeeSupervisor(employeeId);

		assertEquals((Integer)23, e.getEmployeeId());
	}

//	@Test
	public void testGetEmployeesUnderSupervisorIdList() {
		List<Integer> subordinateIds = new ArrayList<>();
		subordinateIds.add(24);
		subordinateIds.add(26);
		
		List<Integer> employeeIds = empDao.getEmployeesUnderSupervisorIdList(subordinateIds);

		LogUtilities.trace(employeeIds.toString());

		assertEquals(1, employeeIds.size());
		
		subordinateIds = new ArrayList<>();
		subordinateIds.add(22);
		
		employeeIds = empDao.getEmployeesUnderSupervisorIdList(subordinateIds);

		LogUtilities.trace(employeeIds.toString());

		assertEquals(3, employeeIds.size());
	}

}

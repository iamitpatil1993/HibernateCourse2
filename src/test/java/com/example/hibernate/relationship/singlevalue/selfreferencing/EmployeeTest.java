package com.example.hibernate.relationship.singlevalue.selfreferencing;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeTest extends BaseTest {

	private static UUID employeeWithManagerEmployeeId = null;
	
	@Test
	public void aEmployeePersistWithoutManagerTest() {
		Employee seniorManagerEmployee = new Employee("Rohit Patil");
		em.persist(seniorManagerEmployee);
		assertNotNull(seniorManagerEmployee.getEmployeeId());

		Employee managerEmployee = new Employee("Amit Patil");
		managerEmployee.setManager(seniorManagerEmployee);
		em.persist(managerEmployee);
		assertNotNull(managerEmployee.getEmployeeId());
		assertTrue(em.contains(managerEmployee));
		
		Employee employee = new Employee("asdafasd asdasd");
		employee.setManager(managerEmployee);
		em.persist(employee);
		assertNotNull(employee.getEmployeeId());
		assertTrue(em.contains(employee));
		
		employeeWithManagerEmployeeId = employee.getEmployeeId();
	}
	
	@Test
	public void bFindTest() {
		Employee employee = em.find(Employee.class, employeeWithManagerEmployeeId);
		assertNotNull(employee);
		assertTrue(em.contains(employee));

		Employee manager = employee.getManager();
		assertNotNull(manager);
		assertTrue(em.contains(manager));
		System.out.println("manager name is :: " + manager.getFullName());
	}
	
}

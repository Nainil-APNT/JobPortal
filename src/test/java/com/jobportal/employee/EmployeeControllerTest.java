package com.jobportal.employee;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;


	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee(1, "John", 1234567890L, "john.do@example.com", "johndoe", "password", null,
				null);

		when(employeeService.createEmployee(employee)).thenReturn(employee);

	}

	@Test
	public void testGetAllEmployee() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "John D", 1234567890L, "john.d@example.com", "nainil", "password1", null, null));
		employees.add(new Employee(2, "Jane Smith", 9876543210L, "jane.smith@example.com", "janesmith", "password123",
				null, null));

		when(employeeService.getAllEmployee()).thenReturn(employees);

	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = new Employee(1, "Joh", 1234567890L, "john@example.com", "jaimin", "password2", null, null);

		when(employeeService.getemployeeById(1)).thenReturn(employee);

	}

	@Test
	public void testUpdateEmployee() {
		Employee existingEmployee = new Employee(1, "Doe", 1234567890L, "ndoe@example.com", "mit", "password3", null,
				null);
		Employee updatedEmployee = new Employee(1, "John Doe Updated", 9876543210L, "john.doe.updated@example.com",
				"johndoeupdated", "newpassword", null, null);

		when(employeeService.updateEmployee(updatedEmployee)).thenReturn(updatedEmployee);
		when(employeeService.getemployeeById(1)).thenReturn(existingEmployee);

	}

}

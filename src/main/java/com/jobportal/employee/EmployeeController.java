package com.jobportal.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Operation(summary = "New Employee Create", description = "Add data for the field of Employee which store in database")
	@PostMapping("/create")
	public ResponseEntity<Employee> createEmployees(@RequestBody Employee employee) {
		Employee savedEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	}

	@Operation(summary = "All Employee's List", description = "This method returns all Employee data from the database")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		List<Employee> employee = employeeService.getAllEmployee();
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@Operation(summary = "Employee Details By ID", description = "This method returns Perticular Employee detail from the database")
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer employeeId) {
		Employee employee = employeeService.getemployeeById(employeeId);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@Operation(summary = "Employee Update By Id", description = "If any Employee details are wrong then change through this method")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer employeeId,
			@RequestBody Employee employee) {
		employee.setEmpid(employeeId);
		Employee updatedemployee = employeeService.updateEmployee(employee);
		return new ResponseEntity<>(updatedemployee, HttpStatus.OK);
	}

	@Operation(summary = "Employee Delete By Id", description = "This method is used for any Employee delete by it's Id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer employeeId) {
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<>("Employee successfully deleted! ", HttpStatus.OK);
	}

	@PostMapping("/filter")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedEmployees(@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedEmployees = employeeService.getFilteredAndPaginatedEmployees(request);
		return ResponseEntity.ok(paginatedEmployees);
	}
	
	
	
	

}

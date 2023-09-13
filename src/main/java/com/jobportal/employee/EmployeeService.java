package com.jobportal.employee;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface EmployeeService {

	Employee createEmployee(Employee employee);

	List<Employee> getAllEmployee();

	Employee getemployeeById(Integer employeeId);

	Employee updateEmployee(Employee employee);

	void deleteEmployee(Integer employeeId);

	AQLResponse<Object> getFilteredAndPaginatedEmployees(AQLRequest request);
	
	

}

package com.jobportal.company;

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
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Operation(summary = "New Company Create", description = "Add data for the field of Company which store in database")
	@PostMapping("/create")
	public ResponseEntity<Company> createCompany(@RequestBody Company company) {
		Company savedCompany = companyService.createCompany(company);
		return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
	}

	@Operation(summary = "All Company's List", description = "This method returns all Company data from the database")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Company>> getAllCompanies() {
		List<Company> company = companyService.getAllCompanies();
		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	@Operation(summary = "Company Details By ID", description = "This method returns Perticular Company detail from the database")
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Company> getCompanyById(@PathVariable("id") Integer companyId) {
		Company company = companyService.getCompanyById(companyId);
		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	@Operation(summary = "Company Update By Id", description = "If any Company details are wrong then change through this method")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Company> updateCompany(@PathVariable("id") Integer companyId, @RequestBody Company company) {
		company.setCompanyId(companyId);
		Company updateCompany = companyService.updateCompany(company);
		return new ResponseEntity<>(updateCompany, HttpStatus.OK);
	}

	@Operation(summary = "Company Delete By Id", description = "This method is used for any Company delete by it's Id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteCompany(@PathVariable("id") Integer companyId) {
		companyService.deleteCompany(companyId);
		return new ResponseEntity<>("Company successfully deleted! ", HttpStatus.OK);
	}

	@PostMapping("/filter")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedCompines(
			@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedCompines = companyService.getFilteredAndPaginatedCompines(request);
		return ResponseEntity.ok(paginatedCompines);
	}
	
	
}

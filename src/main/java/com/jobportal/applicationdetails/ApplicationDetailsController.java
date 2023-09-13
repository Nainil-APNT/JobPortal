package com.jobportal.applicationdetails;

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
@RequestMapping("/applicationdetails")
public class ApplicationDetailsController {

	@Autowired
	private ApplicationDetailsService applicationDetailsService;

	@Operation(summary = "New ApplicationDetails Create", description = "Add data for the field of ApplicationDetails which store in database")
	@PostMapping("/create")
	public ResponseEntity<ApplicationDetails> createApplicationDetails(
			@RequestBody ApplicationDetails applicationDetails) {
		ApplicationDetails savedApplicationDetails = applicationDetailsService
				.createApplicationDetails(applicationDetails);
		return new ResponseEntity<>(savedApplicationDetails, HttpStatus.CREATED);
	}

	@Operation(summary = "All ApplicationDetails List", description = "This method returns all ApplicationDetails data from the database")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<ApplicationDetails>> getAllApplicationDetails() {
		List<ApplicationDetails> applicationDetails = applicationDetailsService.getAllApplicationDetails();
		return new ResponseEntity<>(applicationDetails, HttpStatus.OK);
	}

	@Operation(summary = "ApplicationDetails Details By ID", description = "This method returns Perticular ApplicationDetails detail from the database")
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<ApplicationDetails> getApplicationDetailsById(@PathVariable("id") Integer applicationId) {
		ApplicationDetails applicationDetails = applicationDetailsService.getApplicationDetailsById(applicationId);
		return new ResponseEntity<>(applicationDetails, HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "ApplicationDetails Update By Id", description = "If any ApplicationDetails details are wrong then change through this method")
	public ResponseEntity<ApplicationDetails> updateApplicationDetails(@PathVariable("id") Integer applicationId,
			@RequestBody ApplicationDetails applicationDetails) {
		applicationDetails.setApplicationId(applicationId);
		ApplicationDetails updateApplicationDetails = applicationDetailsService
				.updateApplicationDetails(applicationDetails);
		return new ResponseEntity<>(updateApplicationDetails, HttpStatus.OK);
	}

	@Operation(summary = "ApplicationDetails Delete By Id", description = "This method is used for any ApplicationDetails delete by it's Id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteApplicationDetails(@PathVariable("id") Integer applicationId) {
		applicationDetailsService.deleteApplicationDetails(applicationId);
		return new ResponseEntity<>("Application Details successfully deleted! ", HttpStatus.OK);
	}
	
	@PostMapping("/filter")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedApplicationDetails(@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedApplicationDetails = applicationDetailsService.getFilteredAndPaginatedApplicationDetails(request);
		return ResponseEntity.ok(paginatedApplicationDetails);
	}
	
	
}

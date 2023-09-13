package com.jobportal.jobinformation;

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
@RequestMapping("/jobinformation")
public class JobInformationController {

	@Autowired
	private JobInformationService jobInformationService;

	@PostMapping("/create")
	@Operation(summary = "New JobInformation Create", description = "Add data for the field of JobInformation which store in database")
	public ResponseEntity<JobInformation> createJobInformation(@RequestBody JobInformation jobInformation) {
		JobInformation savedJobInformation = jobInformationService.createJobInformation(jobInformation);
		return new ResponseEntity<>(savedJobInformation, HttpStatus.CREATED);
	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "All JobInformation's List", description = "This method returns all JobInformations data from the database")
	public ResponseEntity<List<JobInformation>> getAllJobInformation() {
		List<JobInformation> jobInformation = jobInformationService.getAllJobInformation();
		return new ResponseEntity<>(jobInformation, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@Operation(summary = "JobInformation Details By ID", description = "This method returns Perticular JobInformation detail from the database")
	public ResponseEntity<JobInformation> getJobInformationById(@PathVariable("id") Integer jobInformationId) {
		JobInformation jobInformation = jobInformationService.getJobInformationById(jobInformationId);
		return new ResponseEntity<>(jobInformation, HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "JobInformation Update By Id", description = "If any JobInformation details are wrong then change through this method")
	public ResponseEntity<JobInformation> updateJobInformation(@PathVariable("id") Integer jobInformationId,
			@RequestBody JobInformation jobInformation) {
		jobInformation.setJobId(jobInformationId);
		JobInformation updateJobInformation = jobInformationService.updateJobInformation(jobInformation);
		return new ResponseEntity<>(updateJobInformation, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "JobInformation Delete By Id", description = "This method is used for any JobInformation delete by it's Id")
	public ResponseEntity<String> deleteJobInformation(@PathVariable("id") Integer jobInformationId) {
		jobInformationService.deleteJobInformation(jobInformationId);
		return new ResponseEntity<>("JobInformation successfully deleted! ", HttpStatus.OK);
	}

	@PostMapping("/filter")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedJobInformationServices(@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedJobInformationServices = jobInformationService.getFilteredAndPaginatedJobInformationServices(request);
		return ResponseEntity.ok(paginatedJobInformationServices);
	}
	
}

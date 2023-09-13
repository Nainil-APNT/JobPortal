package com.jobportal.applicant;

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
@RequestMapping("/applicant")
public class ApplicantController {

	@Autowired
	private ApplicantService applicantService;

	@Operation(summary = "New Applicant Create", description = "Add data for the field of Applicant which store in database")
	@PostMapping("/create")
	public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
		Applicant savedApplicant = applicantService.createApplicant(applicant);
		return new ResponseEntity<>(savedApplicant, HttpStatus.CREATED);
	}

	@Operation(summary = "All Applicant's List", description = "This method returns all Applicants data from the database")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Applicant>> getAllApplicant() {
		List<Applicant> applicant = applicantService.getAllApplicant();
		return new ResponseEntity<>(applicant, HttpStatus.OK);
	}

	@Operation(summary = "Applicant Details By ID", description = "This method returns Perticular Applicants detail from the database")
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Applicant> getApplicantById(@PathVariable("id") Integer applicantId) {
		Applicant applicant = applicantService.getApplicantById(applicantId);
		return new ResponseEntity<>(applicant, HttpStatus.OK);
	}

	@Operation(summary = "Applicant Update By Id", description = "If any Applicant details are wrong then change through this method")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Applicant> updateApplicant(@PathVariable("id") Integer applicantId,
			@RequestBody Applicant applicant) {
		applicant.setApplicantId(applicantId);
		Applicant updateApplicant = applicantService.updateApplicant(applicant);
		return new ResponseEntity<>(updateApplicant, HttpStatus.OK);
	}

	@Operation(summary = "Applicant Delete By Id", description = "This method is used for any applicant delete by it's Id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteApplicant(@PathVariable("id") Integer applicantId) {
		applicantService.deleteApplicant(applicantId);
		return new ResponseEntity<>("Applicant successfully deleted! ", HttpStatus.OK);
	}

	@PostMapping("/filter")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedApplicants(@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedApplicants = applicantService.getFilteredAndPaginatedApplicants(request);
		return ResponseEntity.ok(paginatedApplicants);
	}

}

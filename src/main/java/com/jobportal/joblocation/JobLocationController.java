package com.jobportal.joblocation;

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
@RequestMapping("/joblocation")
public class JobLocationController {

	@Autowired
	private JobLocationService jobLocationService;

	@PostMapping("/create")
	@Operation(summary = "New JobLocation Create", description = "Add data for the field of JobLocation which store in database")
	public ResponseEntity<JobLocation> createJobLocation(@RequestBody JobLocation jobLocation) {
		JobLocation savedJobLocation = jobLocationService.createJobLocation(jobLocation);
		return new ResponseEntity<>(savedJobLocation, HttpStatus.CREATED);
	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "All JobLocation's List", description = "This method returns all JobLocations data from the database")
	public ResponseEntity<List<JobLocation>> getAllJobLocations() {
		List<JobLocation> jobLocation = jobLocationService.getAllJobLocations();
		return new ResponseEntity<>(jobLocation, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@Operation(summary = "JobLocation Details By ID", description = "This method returns Perticular JobLocation detail from the database")
	public ResponseEntity<JobLocation> getJobLocationById(@PathVariable("id") Integer locationId) {
		JobLocation jobLocation = jobLocationService.getJobLocationById(locationId);
		return new ResponseEntity<>(jobLocation, HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "JobLocation Update By Id", description = "If any JobLocation details are wrong then change through this method")
	public ResponseEntity<JobLocation> updateJobLocation(@PathVariable("id") Integer locationId,
			@RequestBody JobLocation jobLocation) {
		jobLocation.setLocationId(locationId);
		JobLocation updatedJobLocation = jobLocationService.updateJobLocation(jobLocation);
		return new ResponseEntity<>(updatedJobLocation, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "JobLocation Delete By Id", description = "This method is used for any JobLocation delete by it's Id")
	public ResponseEntity<String> deleteJobLocation(@PathVariable("id") Integer locationId) {
		jobLocationService.deleteJobLocation(locationId);
		return new ResponseEntity<>("Job Location successfully deleted! ", HttpStatus.OK);
	}

	
	@PostMapping("/filter")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedJobLocations(@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedjobLocations = jobLocationService.getFilteredAndPaginatedJobLocations(request);
		return ResponseEntity.ok(paginatedjobLocations);
	}
	
}

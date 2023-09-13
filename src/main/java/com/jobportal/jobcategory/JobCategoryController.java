package com.jobportal.jobcategory;

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
@RequestMapping("/jobcategory")
public class JobCategoryController {

	@Autowired
	private JobCategoryService jobCategoryService;

	@PostMapping("/create")
	@Operation(summary = "New JobCategory Create", description = "Add data for the field of JobCategory which store in database")
	public ResponseEntity<JobCategory> createJobCategory(@RequestBody JobCategory jobCategory) {
		JobCategory savedJobCategory = jobCategoryService.createJobCategory(jobCategory);
		return new ResponseEntity<>(savedJobCategory, HttpStatus.CREATED);
	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "All JobCategory's List", description = "This method returns all JobCategory data from the database")
	public ResponseEntity<List<JobCategory>> getAllJobCategory() {
		List<JobCategory> jobCategory = jobCategoryService.getAllJobCategory();
		return new ResponseEntity<>(jobCategory, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@Operation(summary = "JobCategory Details By ID", description = "This method returns Perticular JobCategory detail from the database")
	public ResponseEntity<JobCategory> getJobCategoryById(@PathVariable("id") Integer jobCategoryId) {
		JobCategory jobCategory = jobCategoryService.getJobCategoryById(jobCategoryId);
		return new ResponseEntity<>(jobCategory, HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "JobCategory Update By Id", description = "If any JobCategory details are wrong then change through this method")
	public ResponseEntity<JobCategory> updateJobCategory(@PathVariable("id") Integer jobCategoryId,
			@RequestBody JobCategory jobCategory) {
		jobCategory.setCategoryid(jobCategoryId);
		JobCategory updatejobCategory = jobCategoryService.updateJobCategory(jobCategory);
		return new ResponseEntity<>(updatejobCategory, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(summary = "JobCategory Delete By Id", description = "This method is used for any JobCategory delete by it's Id")
	public ResponseEntity<String> deleteJobCategory(@PathVariable("id") Integer jobCategoryId) {
		jobCategoryService.deleteJobCategory(jobCategoryId);
		return new ResponseEntity<>("JobCategory successfully deleted! ", HttpStatus.OK);
	}

	@PostMapping("/filter")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<AQLResponse<Object>> getFilteredAndPaginatedJobCategory(@RequestBody AQLRequest request) {
		AQLResponse<Object> paginatedJobCategory = jobCategoryService.getFilteredAndPaginatedJobCategory(request);
		return ResponseEntity.ok(paginatedJobCategory);
	}
	
}

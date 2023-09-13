package com.jobportal.jobcategory;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface JobCategoryService {

	JobCategory createJobCategory(JobCategory jobCategory);

	List<JobCategory> getAllJobCategory();

	JobCategory getJobCategoryById(Integer jobCategoryId);

	JobCategory updateJobCategory(JobCategory jobCategory);

	void deleteJobCategory(Integer jobCategoryId);

	AQLResponse<Object> getFilteredAndPaginatedJobCategory(AQLRequest request);

}

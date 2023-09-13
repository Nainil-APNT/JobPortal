package com.jobportal.joblocation;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface JobLocationService {

	JobLocation createJobLocation(JobLocation jobLocation);

	List<JobLocation> getAllJobLocations();

	JobLocation getJobLocationById(Integer locationId);

	JobLocation updateJobLocation(JobLocation jobLocation);

	void deleteJobLocation(Integer locationId);

	AQLResponse<Object> getFilteredAndPaginatedJobLocations(AQLRequest request);
}

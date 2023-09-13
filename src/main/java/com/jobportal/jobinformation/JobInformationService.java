package com.jobportal.jobinformation;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface JobInformationService {

	JobInformation createJobInformation(JobInformation jobInformation);

	List<JobInformation> getAllJobInformation();

	JobInformation getJobInformationById(Integer jobInformationId);

	JobInformation updateJobInformation(JobInformation jobInformation);

	void deleteJobInformation(Integer jobInformationId);

	AQLResponse<Object> getFilteredAndPaginatedJobInformationServices(AQLRequest request);
}

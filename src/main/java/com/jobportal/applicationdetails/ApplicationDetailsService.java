package com.jobportal.applicationdetails;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface ApplicationDetailsService {

	ApplicationDetails createApplicationDetails(ApplicationDetails applicationDetails);

	List<ApplicationDetails> getAllApplicationDetails();

	ApplicationDetails getApplicationDetailsById(Integer applicationId);

	ApplicationDetails updateApplicationDetails(ApplicationDetails applicationDetails);

	void deleteApplicationDetails(Integer applicationId);

	AQLResponse<Object> getFilteredAndPaginatedApplicationDetails(AQLRequest request);

}

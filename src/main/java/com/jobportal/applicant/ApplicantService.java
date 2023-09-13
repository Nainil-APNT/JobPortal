package com.jobportal.applicant;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface ApplicantService {

	Applicant createApplicant(Applicant applicant);

	List<Applicant> getAllApplicant();

	Applicant getApplicantById(Integer applicantId);

	Applicant updateApplicant(Applicant applicant);

	void deleteApplicant(Integer applicantId);

	AQLResponse<Object> getFilteredAndPaginatedApplicants(AQLRequest request);

}

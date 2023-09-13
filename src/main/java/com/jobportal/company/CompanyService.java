package com.jobportal.company;

import java.util.List;

import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

public interface CompanyService {

	Company createCompany(Company company);

	List<Company> getAllCompanies();

	Company getCompanyById(Integer companyId);

	Company updateCompany(Company company);

	void deleteCompany(Integer companyId);

	AQLResponse<Object> getFilteredAndPaginatedCompines(AQLRequest request);
}

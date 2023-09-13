package com.jobportal.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.aql.AQLFilter;
import com.jobportal.aql.AQLRequest;
import com.jobportal.aql.AQLResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public Company getCompanyById(Integer companyId) {
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isPresent()) {
			return company.get();
		} else {
			return null;
		}
	}

	@Override
	public Company updateCompany(Company company) {
		Company existingCompany = companyRepository.findById(company.getCompanyId()).orElse(company);
		existingCompany.setCompanyName(company.getCompanyName());
		existingCompany.setCompanyContact(company.getCompanyContact());
		existingCompany.setCompanyAddress(company.getCompanyAddress());
		existingCompany.setCompanyEmail(company.getCompanyEmail());
		existingCompany.setCompanyUsername(company.getCompanyUsername());
		existingCompany.setCompanyPassword(company.getCompanyPassword());
		existingCompany.setCompanyWebsite(company.getCompanyWebsite());
		return companyRepository.save(existingCompany);
	}

	@Override
	public void deleteCompany(Integer companyId) {
		companyRepository.deleteById(companyId);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AQLResponse<Object> getFilteredAndPaginatedCompines(AQLRequest request) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
		Root<Company> companyRoot = criteriaQuery.from(Company.class);

		List<Predicate> predicates = new ArrayList<>();

		List<AQLFilter> filters = request.getFilters();
		if (filters != null) {
			for (AQLFilter filter : filters) {
				Path<String> fieldPath = companyRoot.get(filter.getField());
				Predicate predicate = criteriaBuilder.equal(fieldPath, filter.getValue());
				predicates.add(predicate);
			}
		}

		Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(combinedPredicate);

		TypedQuery<Company> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult((request.getPageNo() - 1) * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());
		List<Company> compines = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(Company.class)));
		Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

		return new AQLResponse(compines, totalRecords);

	}

}

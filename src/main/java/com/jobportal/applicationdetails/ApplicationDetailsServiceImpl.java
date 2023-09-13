package com.jobportal.applicationdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.applicant.Applicant;
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
public class ApplicationDetailsServiceImpl implements ApplicationDetailsService {

	@Autowired
	private ApplicationDetailsRepository applicationDetailsRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ApplicationDetails createApplicationDetails(ApplicationDetails applicationDetails) {
		return applicationDetailsRepository.save(applicationDetails);
	}

	@Override
	public List<ApplicationDetails> getAllApplicationDetails() {
		return applicationDetailsRepository.findAll();
	}

	@Override
	public ApplicationDetails getApplicationDetailsById(Integer applicationId) {
		Optional<ApplicationDetails> applicationDetails = applicationDetailsRepository.findById(applicationId);
		if (applicationDetails.isPresent()) {
			return applicationDetails.get();
		} else {
			return null;
		}
	}

	@Override
	public ApplicationDetails updateApplicationDetails(ApplicationDetails applicationDetails) {
		ApplicationDetails existingApplicationDetails = applicationDetailsRepository
				.findById(applicationDetails.getApplicationId()).orElse(applicationDetails);
		existingApplicationDetails.setApplicationStatus(applicationDetails.getApplicationStatus());
		return applicationDetailsRepository.save(existingApplicationDetails);
	}

	@Override
	public void deleteApplicationDetails(Integer applicationId) {
		applicationDetailsRepository.deleteById(applicationId);

	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AQLResponse<Object> getFilteredAndPaginatedApplicationDetails(AQLRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ApplicationDetails> criteriaQuery = criteriaBuilder.createQuery(ApplicationDetails.class);
		Root<ApplicationDetails> applicationDetailsRoot = criteriaQuery.from(ApplicationDetails.class);

		List<Predicate> predicates = new ArrayList<>();

		List<AQLFilter> filters = request.getFilters();
		if (filters != null) {
			for (AQLFilter filter : filters) {
				Path<String> fieldPath = applicationDetailsRoot.get(filter.getField());
				Predicate predicate = criteriaBuilder.equal(fieldPath, filter.getValue());
				predicates.add(predicate);
			}
		}

		Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(combinedPredicate);

		TypedQuery<ApplicationDetails> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult((request.getPageNo() - 1) * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());
		List<ApplicationDetails> applicationDetails = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(Applicant.class)));
		Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

		return new AQLResponse(applicationDetails, totalRecords);
		
		
	}

}

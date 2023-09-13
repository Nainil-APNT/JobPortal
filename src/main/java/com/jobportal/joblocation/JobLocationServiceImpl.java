package com.jobportal.joblocation;

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
public class JobLocationServiceImpl implements JobLocationService {

	@Autowired
	private JobLocationRepository jobLocationRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JobLocation createJobLocation(JobLocation jobLocation) {
		return jobLocationRepository.save(jobLocation);
	}

	@Override
	public List<JobLocation> getAllJobLocations() {
		return jobLocationRepository.findAll();
	}

	@Override
	public JobLocation getJobLocationById(Integer locationId) {
		Optional<JobLocation> jobLocation = jobLocationRepository.findById(locationId);
		if (jobLocation.isPresent()) {
			return jobLocation.get();
		} else {
			return null;
		}
	}

	@Override
	public JobLocation updateJobLocation(JobLocation jobLocation) {
		JobLocation existingJobLocation = jobLocationRepository.findById(jobLocation.getLocationId())
				.orElse(jobLocation);
		existingJobLocation.setLocationName(jobLocation.getLocationName());
		return jobLocationRepository.save(existingJobLocation);
	}

	@Override
	public void deleteJobLocation(Integer locationId) {
		jobLocationRepository.deleteById(locationId);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AQLResponse<Object> getFilteredAndPaginatedJobLocations(AQLRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<JobLocation> criteriaQuery = criteriaBuilder.createQuery(JobLocation.class);
		Root<JobLocation> jobLocationRoot = criteriaQuery.from(JobLocation.class);

		List<Predicate> predicates = new ArrayList<>();

		List<AQLFilter> filters = request.getFilters();
		if (filters != null) {
			for (AQLFilter filter : filters) {
				Path<String> fieldPath = jobLocationRoot.get(filter.getField());
				Predicate predicate = criteriaBuilder.equal(fieldPath, filter.getValue());
				predicates.add(predicate);
			}
		}

		Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(combinedPredicate);

		TypedQuery<JobLocation> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult((request.getPageNo() - 1) * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());
		List<JobLocation> jobLocations = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(JobLocation.class)));
		Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

		return new AQLResponse(jobLocations, totalRecords);
	}

}

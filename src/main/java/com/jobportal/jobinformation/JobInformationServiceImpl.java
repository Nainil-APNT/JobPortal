package com.jobportal.jobinformation;

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
public class JobInformationServiceImpl implements JobInformationService {

	@Autowired
	private JobInformationRepository jobInformationRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JobInformation createJobInformation(JobInformation jobInformation) {
		return jobInformationRepository.save(jobInformation);
	}

	@Override
	public List<JobInformation> getAllJobInformation() {
		return jobInformationRepository.findAll();
	}

	@Override
	public JobInformation getJobInformationById(Integer jobInformationId) {

		Optional<JobInformation> jobInformation = jobInformationRepository.findById(jobInformationId);
		if (jobInformation.isPresent()) {
			return jobInformation.get();
		} else {
			return null;
		}
	}

	@Override
	public JobInformation updateJobInformation(JobInformation jobInformation) {
		JobInformation existingJobInformation = jobInformationRepository.findById(jobInformation.getJobId())
				.orElse(jobInformation);
		existingJobInformation.setApplicationDate(jobInformation.getApplicationDate());
		existingJobInformation.setDescription(jobInformation.getDescription());
		existingJobInformation.setNoOfVacancy(jobInformation.getNoOfVacancy());
		existingJobInformation.setPositionDate(jobInformation.getPositionDate());
		existingJobInformation.setSalary(jobInformation.getSalary());
		existingJobInformation.setStatus(jobInformation.getStatus());
		existingJobInformation.setTitle(jobInformation.getTitle());
		existingJobInformation.setType(jobInformation.getType());
		return jobInformationRepository.save(existingJobInformation);
	}

	@Override
	public void deleteJobInformation(Integer jobInformationId) {
		jobInformationRepository.deleteById(jobInformationId);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AQLResponse<Object> getFilteredAndPaginatedJobInformationServices(AQLRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<JobInformation> criteriaQuery = criteriaBuilder.createQuery(JobInformation.class);
		Root<JobInformation> jobInformationRoot = criteriaQuery.from(JobInformation.class);

		List<Predicate> predicates = new ArrayList<>();

		List<AQLFilter> filters = request.getFilters();
		if (filters != null) {
			for (AQLFilter filter : filters) {
				Path<String> fieldPath = jobInformationRoot.get(filter.getField());
				Predicate predicate = criteriaBuilder.equal(fieldPath, filter.getValue());
				predicates.add(predicate);
			}
		}

		Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(combinedPredicate);

		TypedQuery<JobInformation> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult((request.getPageNo() - 1) * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());
		List<JobInformation> jobInformations = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(JobInformation.class)));
		Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

		return new AQLResponse(jobInformations, totalRecords);

	}

}

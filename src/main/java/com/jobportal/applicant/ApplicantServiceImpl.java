package com.jobportal.applicant;

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
public class ApplicantServiceImpl implements ApplicantService {

	@Autowired
	private ApplicantRepository applicantRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Applicant createApplicant(Applicant applicant) {
		return applicantRepository.save(applicant);
	}

	@Override
	public List<Applicant> getAllApplicant() {
		return applicantRepository.findAll();
	}

	@Override
	public Applicant getApplicantById(Integer applicantId) {
		Optional<Applicant> applicant = applicantRepository.findById(applicantId);
		if (applicant.isPresent()) {
			return applicant.get();
		} else {
			return null;
		}
	}

	@Override
	public Applicant updateApplicant(Applicant applicant) {
		Applicant existingApplicant = applicantRepository.findById(applicant.getApplicantId()).orElse(applicant);
		existingApplicant.setName(applicant.getName());
		existingApplicant.setContact(applicant.getContact());
		existingApplicant.setEmail(applicant.getEmail());
		existingApplicant.setGender(applicant.getGender());
		existingApplicant.setUsername(applicant.getUsername());
		existingApplicant.setPassword(applicant.getPassword());
		existingApplicant.setHighestEducation(applicant.getHighestEducation());
		existingApplicant.setProfessionalSummary(applicant.getProfessionalSummary());
		return applicantRepository.save(existingApplicant);
	}

	@Override
	public void deleteApplicant(Integer applicantId) {
		applicantRepository.deleteById(applicantId);
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AQLResponse<Object> getFilteredAndPaginatedApplicants(AQLRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Applicant> criteriaQuery = criteriaBuilder.createQuery(Applicant.class);
		Root<Applicant> applicantRoot = criteriaQuery.from(Applicant.class);

		List<Predicate> predicates = new ArrayList<>();

		List<AQLFilter> filters = request.getFilters();
		if (filters != null) {
			for (AQLFilter filter : filters) {
				Path<String> fieldPath = applicantRoot.get(filter.getField());
				Predicate predicate = criteriaBuilder.equal(fieldPath, filter.getValue());
				predicates.add(predicate);
			}
		}

		Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(combinedPredicate);

		TypedQuery<Applicant> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult((request.getPageNo() - 1) * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());
		List<Applicant> applicants = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(Applicant.class)));
		Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

		return new AQLResponse(applicants, totalRecords);
	}


}

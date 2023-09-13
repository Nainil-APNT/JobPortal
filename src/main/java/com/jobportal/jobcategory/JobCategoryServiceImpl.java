package com.jobportal.jobcategory;

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
public class JobCategoryServiceImpl implements JobCategoryService {

	@Autowired
	private JobCategoryRepository jobCategoryRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JobCategory createJobCategory(JobCategory jobCategory) {
		return jobCategoryRepository.save(jobCategory);
	}

	@Override
	public List<JobCategory> getAllJobCategory() {
		return jobCategoryRepository.findAll();
	}

	@Override
	public JobCategory getJobCategoryById(Integer jobCategoryId) {
		Optional<JobCategory> jobCategory = jobCategoryRepository.findById(jobCategoryId);
		if (jobCategory.isPresent()) {
			return jobCategory.get();
		} else {
			return null;
		}
	}

	@Override
	public JobCategory updateJobCategory(JobCategory jobCategory) {
		JobCategory existingjobCategory = jobCategoryRepository.findById(jobCategory.getCategoryid())
				.orElse(jobCategory);
		existingjobCategory.setCategoryname(jobCategory.getCategoryname());
		return jobCategoryRepository.save(existingjobCategory);
	}

	@Override
	public void deleteJobCategory(Integer jobCategoryId) {
		jobCategoryRepository.deleteById(jobCategoryId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AQLResponse<Object> getFilteredAndPaginatedJobCategory(AQLRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<JobCategory> criteriaQuery = criteriaBuilder.createQuery(JobCategory.class);
		Root<JobCategory> jobCategoryRoot = criteriaQuery.from(JobCategory.class);

		List<Predicate> predicates = new ArrayList<>();

		List<AQLFilter> filters = request.getFilters();
		if (filters != null) {
			for (AQLFilter filter : filters) {
				Path<String> fieldPath = jobCategoryRoot.get(filter.getField());
				Predicate predicate = criteriaBuilder.equal(fieldPath, filter.getValue());
				predicates.add(predicate);
			}
		}

		Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(combinedPredicate);

		TypedQuery<JobCategory> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult((request.getPageNo() - 1) * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());
		List<JobCategory> jobCategories = typedQuery.getResultList();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(JobCategory.class)));
		Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

		return new AQLResponse(jobCategories, totalRecords);
	}

}

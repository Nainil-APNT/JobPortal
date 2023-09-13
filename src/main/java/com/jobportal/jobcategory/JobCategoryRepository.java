package com.jobportal.jobcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer>{

}

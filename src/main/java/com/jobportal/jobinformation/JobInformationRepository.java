package com.jobportal.jobinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobInformationRepository extends JpaRepository<JobInformation, Integer>{

}

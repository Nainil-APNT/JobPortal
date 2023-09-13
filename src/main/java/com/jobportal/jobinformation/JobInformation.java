package com.jobportal.jobinformation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.applicationdetails.ApplicationDetails;
import com.jobportal.company.Company;
import com.jobportal.jobcategory.JobCategory;
import com.jobportal.joblocation.JobLocation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "jobinformation")
public class JobInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "job_id")
	private int jobId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "no_of_vacancy")
	private int noOfVacancy;

	@Column(name = "type")
	private String type;

	@Column(name = "salary")
	private int salary;

	@Column(name = "position_date")
	private int positionDate;

	@Column(name = "application_date")
	private int applicationDate;

	@Column(name = "status")
	private String status;

	@JsonIgnore
	@OneToMany(mappedBy = "jobInformation")
	private List<ApplicationDetails> applicationDetails;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	private JobLocation jobLocation;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private JobCategory jobCategory;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Company company;

}

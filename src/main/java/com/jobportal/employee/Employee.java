package com.jobportal.employee;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.jobcategory.JobCategory;
import com.jobportal.joblocation.JobLocation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "employee_id")
	private int empid;

	@Column(name = "name")
	private String name;

	@Column(name="contact")
	private Long contact;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<JobCategory> jobCategory;
	
	@JsonIgnore
	@OneToMany(mappedBy = "employee")
	private List<JobLocation> jobLocations;

}

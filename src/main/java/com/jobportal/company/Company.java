package com.jobportal.company;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.jobinformation.JobInformation;

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
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "company_id")
	private int companyId;
	
	@Column(name = "name")
	private String companyName;
	
	@Column(name="contact")
	private Long companyContact;
	
	@Column(name = "address")
	private String companyAddress;
	
	@Column(name = "email")
	private String companyEmail;
	
	@Column(name = "username")
	private String companyUsername;
	
	@Column(name = "password")
	private String companyPassword;
	
	@Column(name = "website")
	private String companyWebsite;
	

	@JsonIgnore
	@OneToMany(mappedBy = "company")
	private List<JobInformation> jobInformations;

}

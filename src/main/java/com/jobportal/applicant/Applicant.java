package com.jobportal.applicant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobportal.applicationdetails.ApplicationDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "applicant")
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicant_id")
	private int applicantId;

	@Column(name = "name")
	private String name;

	@Column(name = "gender")
	private String gender;

	@Column(name = "contact")
	private int contact;

	@Column(name = "email")
	private String email;

	@Column(name = "professional_summary")
	private String professionalSummary;

	@Column(name = "highest_education")
	private String highestEducation;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@JsonIgnore
	@OneToOne(mappedBy = "applicant")
	private ApplicationDetails applicationDetails;

}

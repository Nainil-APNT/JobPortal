package com.jobportal.aql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AQLFilter {

	private String field;
	private String op;
	private String value;
	
}

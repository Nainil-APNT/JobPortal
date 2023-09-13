package com.jobportal.aql;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AQLRequest {

	private List<AQLFilter> filters;
	private int pageNo;
	private int pageSize;
}

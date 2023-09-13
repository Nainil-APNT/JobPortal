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
public class AQLResponse<T> {
    private List<Object> obj;
    private Long totalRecords;
}

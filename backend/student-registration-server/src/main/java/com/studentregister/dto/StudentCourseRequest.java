package com.studentregister.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseRequest {
	private String firstName;
	private String lastName;
	private int age;
	private String departmentName;
	private List<Long> courseIds;
}

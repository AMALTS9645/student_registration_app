package com.studentregister.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

	private Optional<Long> id;
	private String courseName;

	private String couseDuration;

	private String author;
}

package com.studentregister.service;

import org.springframework.http.ResponseEntity;

import com.studentregister.dto.InputRequest;
import com.studentregister.model.Course;


public interface CourseService {
	public ResponseEntity<Course> addCourse(InputRequest<Course> course);
	public ResponseEntity<Course> updateCourse(Long id, InputRequest<Course> course);
	public ResponseEntity<String> deleteCourse(Long id);
}

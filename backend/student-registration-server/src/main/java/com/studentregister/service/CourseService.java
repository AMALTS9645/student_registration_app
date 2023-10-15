package com.studentregister.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.studentregister.dto.CourseRequest;
import com.studentregister.dto.InputRequest;
import com.studentregister.model.Course;

public interface CourseService {
	public ResponseEntity<Course> addCourse(InputRequest<CourseRequest> course);

	public ResponseEntity<Course> updateCourse(Long id, InputRequest<Course> course);

	public ResponseEntity<String> deleteCourse(Long id);

	public ResponseEntity<Course> getCourseById(Long id);

	public ResponseEntity<List<Course>> getAllCourse();
}

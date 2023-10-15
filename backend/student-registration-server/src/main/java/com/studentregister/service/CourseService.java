package com.studentregister.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.studentregister.dto.CourseRequest;
import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.model.Course;
import com.studentregister.model.Student;

public interface CourseService {
	public ResponseEntity<Course> addCourse(InputRequest<CourseRequest> course);

	public ResponseEntity<Course> updateCourse(Long id, InputRequest<CourseRequest> course);

	public ResponseEntity<String> deleteCourse(Long id);

	public ResponseEntity<Course> getCourseById(Long id);

	public ResponseEntity<List<Course>> getAllCourse();

	public ResponseEntity<List<Course>> bulkAddCourse(List<InputRequest<CourseRequest>> request);

	public ResponseEntity<List<Course>> bulkUpdateCourse(List<InputRequest<CourseRequest>> request);

	public ResponseEntity<String> bulkDeleteCourse(List<Long> ids);
}

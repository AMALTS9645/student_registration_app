package com.studentregister.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.model.Student;

public interface StudentService {
	public ResponseEntity<Student> addStudent(InputRequest<StudentCourseRequest> student);
	public ResponseEntity<Student> updateStudent(Long id,InputRequest<StudentCourseRequest> request);
	public ResponseEntity<String> deleteStudent(Long id);
	public ResponseEntity<Student> editCourseForStudent(Long studentId, Long courseId);
//	public ResponseEntity<Student> addCoursesForStudent(Long studentId, InputRequest<StudentCourse> courseIds);
}

package com.studentregister.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.model.Student;
import com.studentregister.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping("/register")
	public ResponseEntity<Student> addStudent(@RequestBody InputRequest<StudentCourseRequest> request) {
		return studentService.addStudent(request);
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody InputRequest<StudentCourseRequest> request) {
		return studentService.updateStudent(id, request);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		return studentService.deleteStudent(id);
	}
	
	@DeleteMapping("/delete/student/{studId}/course/{courseId}")
	public ResponseEntity<Student> deleteStudent(@PathVariable Long studId, @PathVariable Long courseId) {
		return studentService.editCourseForStudent(studId, courseId);
	}
//	@PutMapping("/edit/course/{id}")
//	public ResponseEntity<Student> editStudentCourses(@PathVariable Long id, @RequestBody StudentCourse courseIds) {
//		return studentService.addCoursesForStudent(id, courseIds);
//	}
}

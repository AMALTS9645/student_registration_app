package com.studentregister.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	@PostMapping("/register/all")
	public ResponseEntity<List<Student>> bulkAddStudent(@RequestBody List<InputRequest<StudentCourseRequest>> request) {
		return studentService.bulkAddStudent(request);
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody InputRequest<StudentCourseRequest> request) {
		return studentService.updateStudent(id, request);
	}
	@PutMapping("/editall")
	public ResponseEntity<List<Student>> bulkUpdateStudent(@RequestBody List<InputRequest<StudentCourseRequest>> request) {
		return studentService.bulkUpdateStudent(request);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		return studentService.deleteStudent(id);
	}
	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteStudent(@RequestBody List<Long> ids) {
		return studentService.bulkDeleteStudent(ids);
	}
	
	@DeleteMapping("/delete/student/{studId}/course/{courseId}")
	public ResponseEntity<Student> deleteStudents(@PathVariable Long studId, @PathVariable Long courseId) {
		return studentService.deleteCourseForStudent(studId, courseId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id){
		return studentService.getStudentById(id);
	}
	@PutMapping("/{sId}/course/{cId}")
	public ResponseEntity<Student> assignCourseToStudent(@PathVariable Long sId, @PathVariable Long cId) {
		return studentService.assignCourseToStudent(sId, cId);
	}
	
	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents(){
		return studentService.getAllStudents();
	}
}

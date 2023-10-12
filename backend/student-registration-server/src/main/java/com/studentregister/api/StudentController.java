package com.studentregister.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentregister.model.Student;
import com.studentregister.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@PostMapping("/register")
	public ResponseEntity<Student> addStudent(@RequestBody Student student){
		return studentService.addStudent(student);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student){
		return studentService.updateStudent(student);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		return studentService.deleteStudent(id);
	}
}

package com.studentregister.service;

import org.springframework.http.ResponseEntity;

import com.studentregister.dto.InputRequest;
import com.studentregister.model.Student;

public interface StudentService {
	public ResponseEntity<Student> addStudent(InputRequest<Student> student);
	public ResponseEntity<Student> updateStudent(Long id,InputRequest<Student> request);
	public ResponseEntity<String> deleteStudent(Long id);
}

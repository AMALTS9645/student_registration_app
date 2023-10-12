package com.studentregister.service;

import org.springframework.http.ResponseEntity;

import com.studentregister.model.Student;

public interface StudentService {
	public ResponseEntity<Student> addStudent(Student student);
	public ResponseEntity<Student> updateStudent(Student student);
	public void deleteStudent(Long id);
}

package com.studentregister.service;

import com.studentregister.model.Student;

public interface StudentService {
	public Student addStudent(Student student);
	public Student updateStudent(Student student);
	public void deleteStudent(Long id);
}

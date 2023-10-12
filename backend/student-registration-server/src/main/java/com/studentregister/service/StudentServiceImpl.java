package com.studentregister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.exception.StudentAlreadyExistsException;
import com.studentregister.model.Student;
import com.studentregister.repository.StudentRespository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRespository studentRepository;

	@Override
	public ResponseEntity<Student> addStudent(Student student) {
		try {
			if (studentRepository.existsById(student.getId())) {
				throw new StudentAlreadyExistsException("Student Already Exist");
			}
			return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<Student> updateStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteStudent(Long id) {
		// TODO Auto-generated method stub

	}

}

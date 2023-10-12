package com.studentregister.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.exception.StudentAlreadyExistsException;
import com.studentregister.exception.StudentNotFoundException;
import com.studentregister.model.Student;
import com.studentregister.repository.StudentRespository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRespository studentRepository;

	@Override
	public ResponseEntity<Student> addStudent(Student student) {
		Optional<Student> stud = studentRepository.findById(student.getId());
		if(!stud.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(student));
		}
		else {
			throw new StudentAlreadyExistsException("Student already exists");
		}
//		try {
//			if (!studentRepository.existsById(student.getId())) {
//				return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
//			}
//			throw new StudentAlreadyExistsException("Student already exist");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ResponseEntity.badRequest().build();
		
	}

	@Override
	public ResponseEntity<Student> updateStudent(Student student) {
		try {
			if (studentRepository.existsById(student.getId())) {
				return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
			}
			throw new StudentNotFoundException("Student Not Found");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<String> deleteStudent(Long id) {
		try {
			if (studentRepository.existsById(id)) {
				studentRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.OK).body("Student deleted");
			}
			throw new StudentNotFoundException("Student Not Found with given id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.badRequest().build();

	}

}

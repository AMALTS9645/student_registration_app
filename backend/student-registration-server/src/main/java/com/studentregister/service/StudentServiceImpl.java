package com.studentregister.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.dto.InputRequest;
import com.studentregister.exception.StudentAlreadyExistsException;
import com.studentregister.exception.StudentNotFoundException;
import com.studentregister.model.Student;
import com.studentregister.repository.StudentRespository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRespository studentRepository;

	@Override
	public ResponseEntity<Student> addStudent(InputRequest<Student> request) {
		String currentUser = request.getLoggedInUser();
		request.setTimeZone(Calendar.getInstance().getTimeZone().getDisplayName());

		Student student = request.getDetails();

		student.setCreatedBy(currentUser);

		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(student));

	}

	@Override
	public ResponseEntity<Student> updateStudent(Long id, InputRequest<Student> request) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			student.get().setLastModifiedBy(request.getLoggedInUser());
			student.get().setFirstName(request.getDetails().getFirstName());
			student.get().setLastName(request.getDetails().getLastName());
			student.get().setAge(request.getDetails().getAge());
			student.get().setDepartmentName(request.getDetails().getDepartmentName());
			return new ResponseEntity<>(studentRepository.saveAndFlush(student.get()), HttpStatus.ACCEPTED);
		} else {
			throw new StudentNotFoundException("Student Not Found with given id " + id);
		}
	}

	@Override
	public ResponseEntity<String> deleteStudent(Long id) {
		try {
			if (studentRepository.existsById(id)) {
				studentRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student with id " +id+ " deleted");
			}
			throw new StudentNotFoundException("Student Not Found with given id " +id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().build();

	}

}

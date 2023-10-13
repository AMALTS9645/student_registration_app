package com.studentregister.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.exception.CourseNotFoundException;
import com.studentregister.exception.StudentAlreadyExistsException;
import com.studentregister.exception.StudentNotFoundException;
import com.studentregister.model.Course;
import com.studentregister.model.Student;
import com.studentregister.repository.CourseRepository;
import com.studentregister.repository.StudentRespository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRespository studentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Override
	public ResponseEntity<Student> addStudent(InputRequest<StudentCourseRequest> request) {
		String currentUser = request.getLoggedInUser();
		request.setTimeZone(Calendar.getInstance().getTimeZone().getDisplayName());

		Student student = new Student();

		student.setCreatedBy(currentUser);
		student.setFirstName(request.getDetails().getFirstName());
		student.setLastName(request.getDetails().getLastName());
		student.setAge(request.getDetails().getAge());
		student.setDepartmentName(request.getDetails().getDepartmentName());

		return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(student));

	}

	@Override
	public ResponseEntity<Student> updateStudent(Long id, InputRequest<StudentCourseRequest> request) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			student.get().setLastModifiedBy(request.getLoggedInUser());
			if (!request.getDetails().getFirstName().isBlank()) {
				student.get().setFirstName(request.getDetails().getFirstName());
			}
			if (!request.getDetails().getLastName().isBlank()) {
				student.get().setLastName(request.getDetails().getLastName());
			}
			if (request.getDetails().getAge() > 0) {
				student.get().setAge(request.getDetails().getAge());
			}
			if (!request.getDetails().getDepartmentName().isBlank()) {
				student.get().setDepartmentName(request.getDetails().getDepartmentName());
			}
			if (!request.getDetails().getCourseIds().isEmpty()) {
				List<Course> coursesToAdd = new ArrayList<>();
				for (Long courseId : request.getDetails().getCourseIds()) {
					Course course = courseRepository.findById(courseId)
							.orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
					coursesToAdd.add(course);
				}

				student.get().getCourses().addAll(coursesToAdd);
			}

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
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student with id " + id + " deleted");
			}
			throw new StudentNotFoundException("Student Not Found with given id " + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().build();

	}

	@Override
	public ResponseEntity<Student> editCourseForStudent(Long studentId, Long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotFoundException("Student not found"));

		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found"));

		if (student.getCourses().contains(course)) {
			student.getCourses().remove(course);
			return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
		} 
		return ResponseEntity.badRequest().build();
	}

///	@Override
///	public ResponseEntity<Student> addCoursesForStudent(Long studentId,InputRequest<StudentCourse>> courseIds) {
///		Student student = studentRepository.findById(studentId)
///				.orElseThrow(() -> new StudentNotFoundException("Student not found with id " +studentId));
//			
///		List<Course> coursesToAdd = new ArrayList<>();
///		for (Long courseId : courseId.getDetails().getCourseIds())) {
///			Course course = courseRepository.findById(courseId)
///					.orElseThrow(() -> newCourseNotFoundExceptionn("Course not found with ID: " + courseId));
///			coursesToAdd.add(course);
///		}
///
///		student.getCourses().addAll(coursesToAdd);
///
///		returnResponseEntity.status(HttpStatus.OK).body((studentRepository.save(student));
///	}

}

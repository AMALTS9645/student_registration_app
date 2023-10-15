package com.studentregister.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.exception.CourseNotFoundException;
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
		Optional<Student> studentOptional = studentRepository.findById(id);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();

			student.setLastModifiedBy(request.getLoggedInUser());
			student.setFirstName(request.getDetails().getFirstName());
			student.setLastName(request.getDetails().getLastName());
			student.setAge(request.getDetails().getAge());
			student.setDepartmentName(request.getDetails().getDepartmentName());
			return new ResponseEntity<>(studentRepository.saveAndFlush(student), HttpStatus.ACCEPTED);
		} else {
			throw new StudentNotFoundException("Student Not Found with given id " + id);
		}
	}

	@Override
	public ResponseEntity<String> deleteStudent(Long id) {
		try {
			Optional<Student> studentOptional = studentRepository.findById(id);
			if (studentOptional.isPresent()) {
				Student student = studentOptional.get();
				studentRepository.delete(student);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student with id " + id + " deleted");
			}
			throw new StudentNotFoundException("Student Not Found with given id " + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().build();

	}

	@Override
	public ResponseEntity<Student> deleteCourseForStudent(Long studentId, Long courseId) {
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

	@Override
	public ResponseEntity<Student> getStudentById(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			return new ResponseEntity<>(student.get(), HttpStatus.FOUND);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Student> assignCourseToStudent(Long sId, Long cId) {

		Set<Course> courses = null;
		Optional<Student> student = studentRepository.findById(sId);
		Optional<Course> course = courseRepository.findById(cId);

		courses = student.get().getCourses();
		courses.add(course.get());

		student.get().setCourses(courses);

		return new ResponseEntity<>(studentRepository.save(student.get()), HttpStatus.OK);

	}

	@Override
	public ResponseEntity<List<Student>> getAllStudents() {
		return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Student>> bulkAddStudent(List<InputRequest<StudentCourseRequest>> request) {
		List<Student> list = new ArrayList<>();
		for (InputRequest<StudentCourseRequest> req : request) {
			ResponseEntity<Student> stud = addStudent(req);
			list.add(stud.getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@Override
	public ResponseEntity<List<Student>> bulkUpdateStudent(List<InputRequest<StudentCourseRequest>> request) {
		List<Student> list = new ArrayList<>();
		for (InputRequest<StudentCourseRequest> req : request) {
			ResponseEntity<Student> stud = updateStudent(req.getDetails().getId().get(), req);
			list.add(stud.getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@Override
	public ResponseEntity<String> bulkDeleteStudent(List<Long> ids) {
		for (Long idItem : ids) {
			deleteStudent(idItem);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Students deleted");
	}

}

package com.studentregister.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
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
		Optional<Student> studentOptional = studentRepository.findById(id);
	    if (studentOptional.isPresent()) {
	        Student student = studentOptional.get();

	        // Update the basic student information
	        student.setLastModifiedBy(request.getLoggedInUser());
	        student.setFirstName(request.getDetails().getFirstName());
	        student.setLastName(request.getDetails().getLastName());
	        student.setAge(request.getDetails().getAge());
	        student.setDepartmentName(request.getDetails().getDepartmentName());

	        // Get the courses associated with the student
	        Set<Course> coursesToAdd = student.getCourses();

	        // Iterate through the course IDs provided in the request and add them to the student's courses
	        for (Long courseId : request.getDetails().getCourseIds()) {
	            Course course = courseRepository.findById(courseId)
	                    .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
	            coursesToAdd.add(course);
	        }

	        // Update the student's courses
	        student.setCourses(coursesToAdd);

	        // Save and flush the updated student
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

				// Remove the student from all courses
				for (Course course : student.getCourses()) {
					course.getStudents().remove(student);
				}

				// Delete the student
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

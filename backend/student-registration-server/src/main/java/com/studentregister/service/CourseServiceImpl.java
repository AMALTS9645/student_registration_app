package com.studentregister.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.dto.InputRequest;
import com.studentregister.exception.CourseNotFoundException;
import com.studentregister.model.Course;
import com.studentregister.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	CourseRepository courseRepository;

	@Override
	public ResponseEntity<Course> addCourse(InputRequest<Course> request) {
		String currentUser = request.getLoggedInUser();
		request.setTimeZone(Calendar.getInstance().getTimeZone().getDisplayName());

		Course course = request.getDetails();

		course.setCreatedBy(currentUser);

		return ResponseEntity.status(HttpStatus.OK).body(courseRepository.save(course));
	}

	@Override
	public ResponseEntity<Course> updateCourse(Long id, InputRequest<Course> request) {
		Optional<Course> course = courseRepository.findById(id);
		if (course.isPresent()) {
			course.get().setLastModifiedBy(request.getLoggedInUser());
			course.get().setCourseName(request.getDetails().getCourseName());
			course.get().setCouseDuration(request.getDetails().getCouseDuration());
			course.get().setAuthor(request.getDetails().getAuthor());
			
			return new ResponseEntity<>(courseRepository.saveAndFlush(course.get()), HttpStatus.ACCEPTED);
		} else {
			throw new CourseNotFoundException("Course Not Found with given id " + id);
		}
	}

	@Override
	public ResponseEntity<String> deleteCourse(Long id) {
		try {
			if (courseRepository.existsById(id)) {
				courseRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course with id " +id+ " deleted");
			}
			throw new CourseNotFoundException("Course Not Found with given id " +id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().build();
	}

	



}

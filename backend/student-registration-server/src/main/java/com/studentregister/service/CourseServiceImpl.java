package com.studentregister.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.studentregister.dto.CommentRequest;
import com.studentregister.dto.CourseRequest;
import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.exception.CommentAlreadyExistsException;
import com.studentregister.exception.CourseNotFoundException;
import com.studentregister.exception.StudentNotFoundException;
import com.studentregister.model.Comment;
import com.studentregister.model.Course;
import com.studentregister.model.Student;
import com.studentregister.repository.CourseRepository;
import com.studentregister.repository.StudentRespository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRespository studentRepository;

	@Override
	public ResponseEntity<Course> addCourse(InputRequest<CourseRequest> request) {
		String currentUser = request.getLoggedInUser();
		request.setTimeZone(Calendar.getInstance().getTimeZone().getDisplayName());

		Course course = new Course();

		course.setCreatedBy(currentUser);
		course.setCourseName(request.getDetails().getCourseName());
		course.setCouseDuration(request.getDetails().getCouseDuration());
		course.setAuthor(request.getDetails().getAuthor());

		return ResponseEntity.status(HttpStatus.OK).body(courseRepository.save(course));
	}

	@Override
	public ResponseEntity<Course> updateCourse(Long id, InputRequest<CourseRequest> request) {
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
			Optional<Course> courseOptional = courseRepository.findById(id);
			if (courseOptional.isPresent()) {
				Course course = courseOptional.get();

				// Remove the course from all students
//		            for (Student student : course.getStudents()) {
//		                student.getCourses().remove(course);
//		            }

				// Delete the course
				courseRepository.delete(course);

				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course with id " + id + " deleted");
			}
			throw new CourseNotFoundException("Course Not Found with given id " + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().build();
	}

	@Override
	public ResponseEntity<Course> getCourseById(Long id) {
		Optional<Course> course = courseRepository.findById(id);
		if (course.isPresent()) {
			return new ResponseEntity<>(course.get(), HttpStatus.FOUND);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Course>> getAllCourse() {
		return ResponseEntity.status(HttpStatus.FOUND).body(courseRepository.findAll());
	}

	@Override
	public ResponseEntity<List<Course>> bulkAddCourse(List<InputRequest<CourseRequest>> request) {
		List<Course> list = new ArrayList<>();
		for (InputRequest<CourseRequest> req : request) {
			ResponseEntity<Course> course = addCourse(req);
			list.add(course.getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@Override
	public ResponseEntity<List<Course>> bulkUpdateCourse(List<InputRequest<CourseRequest>> request) {
		List<Course> list = new ArrayList<>();
		for (InputRequest<CourseRequest> req : request) {
			ResponseEntity<Course> course = updateCourse(req.getDetails().getId().get(), req);
			list.add(course.getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@Override
	public ResponseEntity<String> bulkDeleteCourse(List<Long> ids) {
		for (Long idItem : ids) {
			deleteCourse(idItem);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Courses deleted");
	}
	
	
	@Override
	public ResponseEntity<Course> updateCourseComment(Long id, InputRequest<CommentRequest> request) {
		Optional<Course> courseOptional = courseRepository.findById(id);
		Comment comment = new Comment();
		if (courseOptional.isPresent()) {
			Course course = courseOptional.get();
			List<Comment> comments = course.getComments();
			
			Optional<Student> student = studentRepository.findById(request.getDetails().getStudentId());
			
			comment.setCreatedBy(request.getLoggedInUser());
			comment.setStudentName(student.get().getFirstName()+" "+student.get().getLastName());
			comment.setCourseName(course.getCourseName());
			comment.setText(request.getDetails().getText());
			
			if(!comments.contains(comment)) {
				comments.add(comment);
			}else {
				throw new CommentAlreadyExistsException("Duplicate Comments not allowed from same user");
			}
			
			course.setComments(comments);
			return new ResponseEntity<>(courseRepository.saveAndFlush(course), HttpStatus.ACCEPTED);
		} else {
			throw new CourseNotFoundException("Course Not Found with given id " + id);
		}
	}

	@Override
	public ResponseEntity<List<Course>> updateBulkCourseComment(Long id, List<InputRequest<CommentRequest>> request) {
		List<Course> list = new ArrayList<>();
		for (InputRequest<CommentRequest> req : request) {
			ResponseEntity<Course> course = updateCourseComment(id, req);
			list.add(course.getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}

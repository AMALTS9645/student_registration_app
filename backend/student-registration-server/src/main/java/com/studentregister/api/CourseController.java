package com.studentregister.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentregister.dto.CourseRequest;
import com.studentregister.dto.InputRequest;
import com.studentregister.dto.StudentCourseRequest;
import com.studentregister.model.Course;
import com.studentregister.model.Student;
import com.studentregister.service.CourseService;

@RestController
@RequestMapping("/api/course")
@CrossOrigin("*")
public class CourseController {

	@Autowired
	CourseService courseService;
	
	@PostMapping("/add")
	public ResponseEntity<Course> addCourse(@RequestBody InputRequest<CourseRequest> request) {
		return courseService.addCourse(request);
	}
	@PostMapping("/register/all")
	public ResponseEntity<List<Course>> bulkAddCourse(@RequestBody List<InputRequest<CourseRequest>> request) {
		return courseService.bulkAddCourse(request);
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody InputRequest<CourseRequest> request) {
		return courseService.updateCourse(id, request);
	}
	@PutMapping("/editall")
	public ResponseEntity<List<Course>> bulkUpdateCourse(@RequestBody List<InputRequest<CourseRequest>> request) {
		return courseService.bulkUpdateCourse(request);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
		return courseService.deleteCourse(id);
	}
	
	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteCourse(@RequestBody List<Long> ids) {
		return courseService.bulkDeleteCourse(ids);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Course> getStudentById(@PathVariable Long id){
		return courseService.getCourseById(id);
	}
	
	@GetMapping
	public ResponseEntity<List<Course>> getAllCourse(){
		return courseService.getAllCourse();
	}
}

package com.studentregister.service;

import com.studentregister.model.Course;

public interface CourseService {
	public Course addCourse(Course course);
	public Course updateCourse(Course course);
	public void deleteCourse(Long id);
}

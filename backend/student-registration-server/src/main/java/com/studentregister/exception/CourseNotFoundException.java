package com.studentregister.exception;

public class CourseNotFoundException extends RuntimeException {
	public CourseNotFoundException(String msg) {
		super(msg);
	}
}

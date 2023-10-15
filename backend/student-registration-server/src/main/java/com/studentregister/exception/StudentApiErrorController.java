package com.studentregister.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.studentregister.dto.ErrorResponse;

@RestControllerAdvice
public class StudentApiErrorController {

	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleStudentAlreadyExistsException(StudentAlreadyExistsException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> handleOtherExistsException(Exception ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}
}

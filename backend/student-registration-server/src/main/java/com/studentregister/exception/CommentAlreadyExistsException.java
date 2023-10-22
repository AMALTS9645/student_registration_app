package com.studentregister.exception;

public class CommentAlreadyExistsException extends RuntimeException {
	public CommentAlreadyExistsException(String msg) {
		super(msg);
	}
}

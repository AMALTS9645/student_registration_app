package com.studentregister.service;

import com.studentregister.model.Comment;

public interface CommentService {
	public Comment addComment(Comment comment);
	public Comment updateComment(Comment comment);
	public void deleteComment(Long id);
}

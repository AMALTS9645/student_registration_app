package com.studentregister.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentregister.dto.CommentRequest;
import com.studentregister.dto.CourseRequest;
import com.studentregister.dto.InputRequest;
import com.studentregister.model.Comment;
import com.studentregister.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;

	@PostMapping("/add")
	public ResponseEntity<Comment> addComment(@RequestBody InputRequest<CommentRequest> request) {
		return commentService.addComment(request);
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody InputRequest<CommentRequest> request) {
		return commentService.updateComment(id, request);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Long id) {
		return commentService.deleteComment(id);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
		return commentService.getCommentById(id);
	}
	@GetMapping
	public ResponseEntity<List<Comment>> getAllComment() {
		return commentService.getAllComment();
	}
}

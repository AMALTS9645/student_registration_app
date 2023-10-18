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

import com.studentregister.dto.CommentRequest;
import com.studentregister.dto.CourseRequest;
import com.studentregister.dto.InputRequest;
import com.studentregister.model.Comment;
import com.studentregister.model.Course;
import com.studentregister.service.CommentService;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentController {
	
	@Autowired
	CommentService commentService;

	@PostMapping("/add")
	public ResponseEntity<Comment> addComment(@RequestBody InputRequest<CommentRequest> request) {
		return commentService.addComment(request);
	}
	@PostMapping("/add/all")
	public ResponseEntity<List<Comment>> bulkAddComment(@RequestBody List<InputRequest<CommentRequest>> request) {
		return commentService.bulkAddComment(request);
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody InputRequest<CommentRequest> request) {
		return commentService.updateComment(id, request);
	}
	@PutMapping("/editall")
	public ResponseEntity<List<Comment>> bulkUpdateComment(@RequestBody List<InputRequest<CommentRequest>> request) {
		return commentService.bulkUpdateComment(request);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Long id) {
		return commentService.deleteComment(id);
	}
	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteComment(@RequestBody List<Long> ids) {
		return commentService.bulkDeleteComment(ids);
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

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
import com.studentregister.exception.CommentNotFoundException;
import com.studentregister.model.Comment;
import com.studentregister.model.Course;
import com.studentregister.model.Student;
import com.studentregister.repository.CommentRepository;
import com.studentregister.repository.CourseRepository;
import com.studentregister.repository.StudentRespository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired	
	StudentRespository studentRepository;
	
	@Autowired
	CourseRepository courseRepository;



	@Override
	public ResponseEntity<Comment> updateComment(Long id, InputRequest<CommentRequest> comment) {
		Optional<Comment> commentOpt = commentRepository.findById(id);
		if(commentOpt.isPresent()) {
			Comment com = commentOpt.get();
			com.setLastModifiedBy(comment.getLoggedInUser());
			com.setText(comment.getDetails().getText());
			
			return new ResponseEntity<>(commentRepository.saveAndFlush(com), HttpStatus.ACCEPTED);
		}else {
			throw new CommentNotFoundException("Comment Not Found with given id " + id);
		}
	}
	

	@Override
	public ResponseEntity<String> deleteComment(Long id) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		if (commentOptional.isPresent()) {
			commentRepository.deleteById(id);
			return new ResponseEntity<>("comment deleted", HttpStatus.NO_CONTENT);
		}else {
			throw new CommentNotFoundException("Comment not found");
		}
	}
//
//	@Override
//	public ResponseEntity<Comment> getCommentById(Long id) {
//		Optional<Comment> commentOptional = commentRepository.findById(id);
//		if (commentOptional.isPresent()) {
//			return new ResponseEntity<>(commentOptional.get(), HttpStatus.FOUND);
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@Override
//	public ResponseEntity<List<Comment>> getAllComment() {
//		return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<List<Comment>> bulkAddComment(List<InputRequest<CommentRequest>> request) {
//		List<Comment> list = new ArrayList<>();
//		for (InputRequest<CommentRequest> req : request) {
//			ResponseEntity<Comment> comment = addComment(req);
//			list.add(comment.getBody());
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
//
//	@Override
//	public ResponseEntity<List<Comment>> bulkUpdateComment(List<InputRequest<CommentRequest>> request) {
//		List<Comment> list = new ArrayList<>();
//		for (InputRequest<CommentRequest> req : request) {
//			ResponseEntity<Comment> comment = updateComment(req.getDetails().getId().get(), req);
//			list.add(comment.getBody());
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
//
//	@Override
//	public ResponseEntity<String> bulkDeleteComment(List<Long> ids) {
//		for (Long idItem : ids) {
//			deleteComment(idItem);
//		}
//		return ResponseEntity.status(HttpStatus.OK).body("Comments deleted");
//	}

}

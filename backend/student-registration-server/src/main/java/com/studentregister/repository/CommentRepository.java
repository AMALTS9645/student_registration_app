package com.studentregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentregister.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}

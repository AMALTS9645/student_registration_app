package com.studentregister.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class CommentRequest {

	private Long studentId;
	private Long courseId;
	private String text;
	
}

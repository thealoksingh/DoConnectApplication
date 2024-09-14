package com.hcl.doconnect.service;

import com.hcl.doconnect.controller.ApiResponse;
import com.hcl.doconnect.dto.AnswerDTO;
import com.hcl.doconnect.dto.CommentDTO;
import com.hcl.doconnect.exception.AnswerNotFoundException;
import com.hcl.doconnect.model.Answer;
import com.hcl.doconnect.model.Comment;

public interface AnswerService {


	ApiResponse<Object> save(AnswerDTO answerDTO);
 
	ApiResponse<?> likeAnswerById(Long answerId);
	ApiResponse<Comment> addCommentToAnswer(CommentDTO commentDTO) throws AnswerNotFoundException;
	
}

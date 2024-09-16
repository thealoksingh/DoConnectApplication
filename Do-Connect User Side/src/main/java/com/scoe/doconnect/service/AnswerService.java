package com.scoe.doconnect.service;

import com.scoe.doconnect.controller.ApiResponse;
import com.scoe.doconnect.dto.AnswerDTO;
import com.scoe.doconnect.dto.CommentDTO;
import com.scoe.doconnect.exception.AnswerNotFoundException;
import com.scoe.doconnect.model.Answer;
import com.scoe.doconnect.model.Comment;

public interface AnswerService {


	ApiResponse<Object> save(AnswerDTO answerDTO);
 
	ApiResponse<?> likeAnswerById(Long answerId);
	ApiResponse<Comment> addCommentToAnswer(CommentDTO commentDTO) throws AnswerNotFoundException;
	
}

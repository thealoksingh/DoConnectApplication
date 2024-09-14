package com.hcl.doconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hcl.doconnect.controller.ApiResponse;
import com.hcl.doconnect.dto.AnswerDTO;
import com.hcl.doconnect.dto.CommentDTO;
import com.hcl.doconnect.exception.AnswerNotFoundException;
import com.hcl.doconnect.model.Comment;

@Service
public class AnswerServiceImpl implements AnswerService {

	
	@Autowired
	private RestTemplate restTemplate;
	
	private final String ADMIN_SERVICE_URL = "http://Do-Connect-Admin/api/admin/";

	

	@Override
    public ApiResponse<Object> save(AnswerDTO answerDTO) {
		
		ApiResponse  response = restTemplate.postForObject(ADMIN_SERVICE_URL+"user/add/answer", answerDTO, ApiResponse.class);
		return response;
    }



	@Override
	public ApiResponse<?> likeAnswerById(Long answerId) {

		ApiResponse  response = restTemplate.getForObject(ADMIN_SERVICE_URL+"user/like/answer/"+answerId,  ApiResponse.class);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResponse<Comment> addCommentToAnswer(CommentDTO commentDTO) throws AnswerNotFoundException {
		try {
			
			return restTemplate.postForObject(ADMIN_SERVICE_URL+"user/answer/comment", commentDTO, ApiResponse.class);
		}catch(HttpClientErrorException e) {
			throw new AnswerNotFoundException("Answer not found with given id = "+commentDTO.getAnswerId());
		}
		
	}
	

}

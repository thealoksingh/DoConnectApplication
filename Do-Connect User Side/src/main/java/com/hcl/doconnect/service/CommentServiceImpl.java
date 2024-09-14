package com.hcl.doconnect.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hcl.doconnect.controller.ApiResponse;
import com.hcl.doconnect.dto.CommentDTO;
import com.hcl.doconnect.exception.AnswerNotFoundException;
import com.hcl.doconnect.model.Answer;
import com.hcl.doconnect.model.Comment;
import com.hcl.doconnect.model.User;
import com.hcl.doconnect.repository.AnswerRepository;
import com.hcl.doconnect.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
 
    @Autowired
    private AnswerRepository answerRepository;
 
 
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
	private RestTemplate restTemplate;

	private String adminServiceUrl = "http://Do-Connect-Admin/";
    
 
    @Override
    @Transactional
    public Comment addComment(Long answerId, CommentDTO commentDTO, User user) throws AnswerNotFoundException {
       

		ApiResponse response = restTemplate.postForObject(adminServiceUrl + "api/admin/user/answer/comment", commentDTO,
				ApiResponse.class);

    	Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + answerId));
 
        User currentUser = user;// You need to get the current user, either from authentication or another method
 
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setAnswer(answer);
        comment.setUser(currentUser);
 
        return commentRepository.save(comment);
    }

	
}

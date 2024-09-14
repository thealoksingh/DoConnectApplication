package com.hcl.doconnect.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.doconnect.dto.CommentDTO;
import com.hcl.doconnect.exception.AnswerNotFoundException;
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
	
	@Override
	@Transactional
	public Comment addComment(Long answerId, CommentDTO commentDTO, User user) throws AnswerNotFoundException {
		var answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + answerId));

		if (answer.getQuestion().getStatus().equals("APPROVED"))
		{

			User currentUser = user;// You need to get the current user, either from authentication or another
									// method

			var comment = new Comment();
			comment.setContent(commentDTO.getContent());
			comment.setAnswer(answer);
			comment.setUser(currentUser);

			return commentRepository.save(comment);
		}
		return null;
	}

	@Override
	public List<Comment> getCommentWithAnswerId(Long answerId) throws AnswerNotFoundException {
		if (answerRepository.findById(answerId).isEmpty()) {
			throw new AnswerNotFoundException("Answer not found with given id = "+answerId);
		}
			
		return commentRepository.findByAnswerId(answerId);
	}

}

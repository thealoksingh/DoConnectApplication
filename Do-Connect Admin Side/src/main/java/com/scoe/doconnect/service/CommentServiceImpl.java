package com.scoe.doconnect.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoe.doconnect.dto.CommentDTO;
import com.scoe.doconnect.exception.AnswerNotFoundException;
import com.scoe.doconnect.model.Comment;
import com.scoe.doconnect.model.User;
import com.scoe.doconnect.repository.AnswerRepository;
import com.scoe.doconnect.repository.CommentRepository;

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

package com.hcl.doconnect.service;

import java.util.List;

import com.hcl.doconnect.dto.QuestionDetailsDTO;
import com.hcl.doconnect.exception.QuestionNotFoundException;
import com.hcl.doconnect.model.Question;

public interface QuestionService {
    Question findById(Long id) throws QuestionNotFoundException;
    List<Question> findAll();
	Question saveQuestion(Question question);
	List<Question> searchQuestions(String searchString);
	
	Question updateQuestion(Long id, Question question) throws QuestionNotFoundException;
	void deleteQuestion(Long id) throws QuestionNotFoundException;
	boolean approveQuestion(Long id) throws QuestionNotFoundException;
	boolean closeDiscussion(Long id) throws QuestionNotFoundException;
	QuestionDetailsDTO getQuestionDetails(long id) throws QuestionNotFoundException;
	
	List<Question> getAllApprovedQuestions();
	List<Question> getAllPendingQuestions();
	List<Question> getAllResolvedQuestions();
	List<Question> searchQuestionsByTopic(String word);
	Question findByContent(Question question);
	boolean existById(Long id);
}

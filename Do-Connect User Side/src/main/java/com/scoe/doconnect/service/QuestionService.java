package com.scoe.doconnect.service;

import java.util.List;

import com.scoe.doconnect.dto.QuestionDTO;
import com.scoe.doconnect.dto.QuestionDetailsDTO;
import com.scoe.doconnect.exception.QuestionNotFoundException;
import com.scoe.doconnect.model.Question;

public interface QuestionService {
    
	
	Question findById(Long id) throws QuestionNotFoundException;
   
	Question saveQuestion(QuestionDTO questionDTO);
	List<Question> searchQuestions(String searchString);
	
	QuestionDetailsDTO getQuestionDetails(long id) throws QuestionNotFoundException;
	List<Question> getAllApprovedQuestions();

	List<Question> searchQuestionsByTopic(String word);

}

package com.hcl.doconnect.service;

import java.util.List;

import com.hcl.doconnect.dto.QuestionDTO;
import com.hcl.doconnect.dto.QuestionDetailsDTO;
import com.hcl.doconnect.exception.QuestionNotFoundException;
import com.hcl.doconnect.model.Question;

public interface QuestionService {
    
	
	Question findById(Long id) throws QuestionNotFoundException;
   
	Question saveQuestion(QuestionDTO questionDTO);
	List<Question> searchQuestions(String searchString);
	
	QuestionDetailsDTO getQuestionDetails(long id) throws QuestionNotFoundException;
	List<Question> getAllApprovedQuestions();

	List<Question> searchQuestionsByTopic(String word);

}

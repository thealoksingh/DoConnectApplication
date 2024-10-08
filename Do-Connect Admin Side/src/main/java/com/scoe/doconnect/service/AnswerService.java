package com.scoe.doconnect.service;

import java.util.List;

import com.scoe.doconnect.exception.AnswerNotFoundException;
import com.scoe.doconnect.model.Answer;

public interface AnswerService {

	Answer findById(Long id) throws AnswerNotFoundException;
    Answer save(Answer answer);
    void delete(Answer answer);
    // Add other methods as needed
	boolean approveAnswer(Long id);
	boolean deleteAnswer(Long id);
	List<Answer> getAllPendingAnswer();
	List<Answer> getAllApprovedAnswer();
	List<Answer> viewAnswersWithQuestionId(Long questionId);
	boolean checkLikedByUser(Long answerId, Long userId);
	void saveLikeByUser(Long ansId, Long userId);
}

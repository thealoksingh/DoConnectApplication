package com.hcl.doconnect.service;

import com.hcl.doconnect.repository.AnswerRepository;
import com.hcl.doconnect.repository.LikeRepository;
import com.hcl.doconnect.repository.QuestionRepository;
import com.hcl.doconnect.exception.AnswerNotFoundException;
import com.hcl.doconnect.model.Answer;
import com.hcl.doconnect.model.Like;
import com.hcl.doconnect.model.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private LikeRepository likeRepository;

	@Override
	public Answer findById(Long id) throws AnswerNotFoundException {
		Answer optionalAnswer = answerRepository.findById(id).orElseThrow(()-> new AnswerNotFoundException(String.format("Answer Not found with given id = %d",id)) );
		return optionalAnswer;
	}

	@Override
    public Answer save(Answer answer) {
    	 Optional<Question> optionalQuestion = questionRepository.findById(answer.getQuestion().getId());
         if (optionalQuestion.isPresent()) {
        	 answerRepository.save(answer);
        	 return answer;
    }
         return null;
	}

	@Override
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}

	@Override
	 public boolean approveAnswer(Long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            answer.setStatus("APPROVED"); // Set the answer as approved
            answerRepository.save(answer);
            return true;
        }
        return false;
    }

	@Override
	public boolean deleteAnswer(Long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            answerRepository.delete(optionalAnswer.get());
            return true;
        }
        return false;
    }

	@Override
	public List<Answer> getAllPendingAnswer() {
		return answerRepository.findByStatus("PENDING");
		
	}

	@Override
	public List<Answer> getAllApprovedAnswer() {
		return answerRepository.findByStatus("APPROVED");
		
	}

	@Override
	public List<Answer> viewAnswersWithQuestionId(Long questionId) {
		Optional<Question> question = questionRepository.findById(questionId);
		if(question.isEmpty()) return null;
		return answerRepository.findByQuestionId(questionId);
		
	}

	@Override
	public boolean checkLikedByUser(Long answerId, Long questionId) {
		var like = likeRepository.findByAnswerIdAndUserId(answerId,questionId);
		if(like != null) return true;
		return false;
	}

	@Override
	public void saveLikeByUser(Long ansId, Long userId) {
		likeRepository.save(new Like(userId,ansId, LocalDateTime.now()));
		
	}

	// Add other methods as needed
}

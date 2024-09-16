package com.scoe.doconnect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoe.doconnect.dto.AnswerDetailsDTO;
import com.scoe.doconnect.dto.QuestionDetailsDTO;
import com.scoe.doconnect.exception.QuestionNotFoundException;
import com.scoe.doconnect.model.Answer;
import com.scoe.doconnect.model.Comment;
import com.scoe.doconnect.model.Question;
import com.scoe.doconnect.repository.AnswerRepository;
import com.scoe.doconnect.repository.CommentRepository;
import com.scoe.doconnect.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private CommentRepository commentRepository;

	private final String APPROVED = "APPROVED";
	private final String PENDING = "PENDING";
	private final String RESOLVED = "RESOLVED";

	@Override
	public Question findById(Long id) throws QuestionNotFoundException {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		return optionalQuestion
				.orElseThrow(() -> new QuestionNotFoundException("Question Not Found With Give id = " + id));
	}
	
	
	@Override
	public boolean existById(Long id) {
		return questionRepository.existsById(id);
	}

	@Override
	public List<Question> findAll() {
		return questionRepository.findAll();
	}

	@Override
	public Question saveQuestion(Question question) {

		return questionRepository.save(question);
	}

	@Override
	public List<Question> searchQuestions(String searchString) {

		List<Question> approvedQuestions = questionRepository.findByContentContainingIgnoreCaseAndStatus(searchString,
				APPROVED);

		return approvedQuestions;
	}

	@Override
	public List<Question> getAllApprovedQuestions() {
		return questionRepository.findByStatus(APPROVED);
	}

	@Override
	public Question updateQuestion(Long id, Question question) throws QuestionNotFoundException {
		var existingQuestion = findById(id);
		if (existingQuestion != null) {
			question.setId(id);
			question.setUser(existingQuestion.getUser());
			question.setAnswers(existingQuestion.getAnswers());
			return questionRepository.save(question);
		}
		return existingQuestion;
	}

	@Override
	public void deleteQuestion(Long id) throws QuestionNotFoundException {
		boolean existingQuestion = questionRepository.existsById(id);
		if (existingQuestion) {
			questionRepository.deleteById(id);
			return;
		}
		throw new QuestionNotFoundException("Question not found with given id = "+id) ;
	}

	@Override
	public boolean approveQuestion(Long id) throws QuestionNotFoundException {
		var question = findById(id);
		
		if (question != null) {
			
			question.setStatus(APPROVED); // Set the question status as approved
			questionRepository.save(question);
			return true;
		}
		return false;
	}

	@Override
	public boolean closeDiscussion(Long id) throws QuestionNotFoundException {
		var question = findById(id);
		if (question != null) {
			
			question.setStatus(RESOLVED); // Update the status of the question as resolved
			questionRepository.save(question);
			return true;
		}
		return false;
	}

	@Override
	public QuestionDetailsDTO getQuestionDetails(long id) throws QuestionNotFoundException {

		var questionDetailsDTO = new QuestionDetailsDTO();
		Question question = findById(id);
		if (question != null) {
			questionDetailsDTO.setQuestion(question);

			List<AnswerDetailsDTO> ansDetailsDTOList = new ArrayList<>();

			List<Answer> answers = answerRepository.findByQuestionId(id);
		

			if (answers != null) {

				for (Answer ans : answers) {
					 var answerDetailsDTO = new AnswerDetailsDTO();
					answerDetailsDTO.setAnswer(ans);

					List<Comment> comments = commentRepository.findByAnswerId(ans.getId());

					if (comments != null) {

						answerDetailsDTO.setComments(comments);

					}

					ansDetailsDTOList.add(answerDetailsDTO);
				}

				
				questionDetailsDTO.setAnswers(ansDetailsDTOList);
				return questionDetailsDTO;
			}
			return questionDetailsDTO;
		}

		return null;

	}

	@Override
	public List<Question> getAllPendingQuestions() {
		return questionRepository.findByStatus(PENDING);

	}

	@Override
	public List<Question> getAllResolvedQuestions() {
		return questionRepository.findByStatus(RESOLVED);

	}

	@Override
	public List<Question> searchQuestionsByTopic(String word) {
		List<Question> approvedQuestions = questionRepository.findByTopicContainingIgnoreCaseAndStatus(word,
				APPROVED);

		return approvedQuestions;
	}

	@Override
	public Question findByContent(Question question) {
		List<Question> questions = questionRepository.findByContentContainingIgnoreCaseAndTopicContainingIgnoreCase(question.getContent(), question.getTopic());
		for(Question q: questions) {
			if(q.getContent().equalsIgnoreCase(question.getContent()) && q.getTopic().equalsIgnoreCase(question.getTopic())){
				return new Question();
			}
		}
		return null;
	}
	
	

	// Add other methods as needed
}

package com.hcl.doconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hcl.doconnect.dto.QuestionDTO;
import com.hcl.doconnect.dto.QuestionDetailsDTO;
import com.hcl.doconnect.exception.QuestionNotFoundException;
import com.hcl.doconnect.model.Question;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private RestTemplate restTemplate;
	
	

	private final String ADMIN_SERVICE_URL = "http://Do-Connect-Admin/api/admin/";

	@Override
	public Question findById(Long id) throws QuestionNotFoundException {

		Question question = restTemplate.getForObject(ADMIN_SERVICE_URL + "user/question/" + id,
				Question.class);

		return question;
	}

	@Override
	public Question saveQuestion(QuestionDTO questionDTO) {
		
//		calling Admin Microservice for processing the data
		return restTemplate.postForObject(ADMIN_SERVICE_URL + "user/question/ask", questionDTO,
				Question.class);

	}

	@Override
	public List<Question> searchQuestions(String searchString) {

		

//		calling Admin Microservice for processing the data
		List<Question> questions = restTemplate
				.getForObject(ADMIN_SERVICE_URL + "user/questions/search/" + searchString, List.class);
		return questions;
	}

	@Override
	public List<Question> getAllApprovedQuestions() {
	
//		calling Admin Microservice for processing the data
		List<Question> approvedQuestions = restTemplate
				.getForObject(ADMIN_SERVICE_URL+"user/questions/viewall", List.class);

		return approvedQuestions;
	}

	@Override
	public QuestionDetailsDTO getQuestionDetails(long id) throws QuestionNotFoundException {


		try {

			return restTemplate.getForObject(ADMIN_SERVICE_URL + "user/question/" + id + "/details",
					QuestionDetailsDTO.class);
		} catch (Exception e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> searchQuestionsByTopic(String word) {
		

//		calling Admin Microservice for processing the data
		List<Question> questions = restTemplate
				.getForObject(ADMIN_SERVICE_URL + "user/questions/searchByTopic/" + word, List.class);
		return questions;
	}

	// Add other methods as needed
}

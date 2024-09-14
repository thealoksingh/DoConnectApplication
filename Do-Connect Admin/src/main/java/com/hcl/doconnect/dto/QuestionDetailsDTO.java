package com.hcl.doconnect.dto;

import java.util.List;

import com.hcl.doconnect.model.Question;

public class QuestionDetailsDTO {
	private Question question;
	private List<AnswerDetailsDTO> answers;
	
	
	public QuestionDetailsDTO(Question question, List<AnswerDetailsDTO> answers) {
		this();
		this.question = question;
		this.answers = answers;
	}
	public QuestionDetailsDTO() {
		super();
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<AnswerDetailsDTO> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswerDetailsDTO> answers) {
		this.answers = answers;
	}
	

}

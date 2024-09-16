package com.scoe.doconnect.dto;

import java.util.List;

import com.scoe.doconnect.model.Question;

public class QuestionDetailsDTO {
	private Question question;
	private List<AnswerDetailsDTO> answers;
	
	
	public QuestionDetailsDTO(Question question, List<AnswerDetailsDTO> answers) {
		super();
		this.question = question;
		this.answers = answers;
	}
	public QuestionDetailsDTO() {
		// TODO Auto-generated constructor stub
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

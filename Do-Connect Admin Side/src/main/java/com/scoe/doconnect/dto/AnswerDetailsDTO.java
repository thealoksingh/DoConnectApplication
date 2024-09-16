package com.scoe.doconnect.dto;

import java.util.List;

import com.scoe.doconnect.model.*;

public class AnswerDetailsDTO {
	
	private Answer answer;
	
	private List<Comment> comments;

	public AnswerDetailsDTO(Answer answer, List<Comment> comments) {
		this();
		this.answer = answer;
		this.comments = comments;
	}

	public AnswerDetailsDTO() {
		super();
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	

}

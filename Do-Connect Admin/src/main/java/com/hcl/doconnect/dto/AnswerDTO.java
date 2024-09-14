package com.hcl.doconnect.dto;

public class AnswerDTO {
	
    private String content;
    private Long questionId;

    // Constructors, getters, and setters

    public AnswerDTO() {
    }

    public AnswerDTO(String content, Long questionId) {
    	this();
        this.content = content;
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}


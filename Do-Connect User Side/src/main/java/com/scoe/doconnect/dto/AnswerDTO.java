package com.scoe.doconnect.dto;

public class AnswerDTO {
    private String content;
    private Long questionId;

    // Constructors, getters, and setters
    // You can generate these using your IDE or manually implement them

    public AnswerDTO() {
    }

    public AnswerDTO(String content, Long questionId) {
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


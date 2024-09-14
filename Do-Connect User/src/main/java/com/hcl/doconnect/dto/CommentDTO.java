package com.hcl.doconnect.dto;

public class CommentDTO {
 
    private Long answerId;
    private String content;
 
    // Constructors, getters, and setters
    public CommentDTO() {
    }
 
    public CommentDTO(Long answerId, String content) {
        this.answerId = answerId;
        this.content = content;
    }
 
    public Long getAnswerId() {
        return answerId;
    }
 
    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
}

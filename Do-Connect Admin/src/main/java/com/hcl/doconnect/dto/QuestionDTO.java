package com.hcl.doconnect.dto;

public class QuestionDTO {
    private String topic;
    private String content;

    
    public QuestionDTO() {
		super();
		
	}

	// Constructors, getters, and setters
    // Constructor
    public QuestionDTO(String topic, String content) {
    	this();
        this.topic = topic;
        this.content = content;
    }

    // Getters and setters
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}



package com.scoe.doconnect.dto;

public class QuestionDTO {
    private String topic;
    private String content;

    
    public QuestionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructors, getters, and setters
    // Constructor
    public QuestionDTO(String topic, String content) {
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

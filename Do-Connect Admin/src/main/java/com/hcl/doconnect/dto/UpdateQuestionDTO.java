package com.hcl.doconnect.dto;

public class UpdateQuestionDTO {
	
	private String topic;
	private String content;
	private String status;
	
	
	public UpdateQuestionDTO() {
		super();
	}

	public UpdateQuestionDTO(String topic, String content, String status) {
		this();
		this.topic = topic;
		this.content = content;
		this.status = status;
	
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
	
	

}

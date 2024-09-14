package com.hcl.doconnect.dto;

public class SendChatDTO {

	private Long toUserId;
	private String message;
	
	public SendChatDTO(Long toUserId, String message) {
		this();
		this.toUserId = toUserId;
		this.message = message;
	}
	public SendChatDTO() {
		super();
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SendChatDTO [toUserId=" + toUserId + ", message=" + message + "]";
	}
	
	
}

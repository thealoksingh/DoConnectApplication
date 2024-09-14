package com.hcl.doconnect.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String message;
	private String status;
	private LocalDateTime time;
	public Notification() {
		super();
	}
	public Notification( String message, String status, LocalDateTime time) {
		super();
	
		this.message = message;
		this.status = status;
		this.time = time;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Notification [id=" + id + ", message=" + message + ", status=" + status + ", time=" + time + "]";
	}
	
	
	


}

package com.scoe.doconnect.model;

import javax.persistence.*;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "chat")
public class Chat {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private User from;
 
    @ManyToOne
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private User to;
 
    @Column
    private LocalDateTime time;
 
    @Column
    private String status;
 
    @Column
    private String message;
 
    // Constructors, getters, and setters
 
    public Chat() {
    }

	public Chat( User from, User to, LocalDateTime time, String status, String message) {
		super();
		
		this.from = from;
		this.to = to;
		this.time = time;
		this.status = status;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
   
}

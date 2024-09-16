package com.scoe.doconnect.model;

import java.time.LocalDateTime;

import javax.persistence.*;
 
@Entity
@Table(name = "user_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private Long userId;
 
    private Long answerId;
 
    private LocalDateTime likedAt = LocalDateTime.now();

	public Like(Long userId, Long answerId, LocalDateTime likedAt) {
		super();
		this.userId = userId;
		this.answerId = answerId;
		this.likedAt = likedAt;
	}

	public Like() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public LocalDateTime getLikedAt() {
		return likedAt;
	}

	public void setLikedAt(LocalDateTime likedAt) {
		this.likedAt = likedAt;
	}

	
    // getters and setters
    
    
}

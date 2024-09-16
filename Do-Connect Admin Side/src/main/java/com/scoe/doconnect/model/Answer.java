package com.scoe.doconnect.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "answers")
public class Answer {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String content;

	    private int likes;

	    private String status; // e.g., approved, pending, rejected

	    // Add comment field
	    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Comment> comments;

	    // Define relationships
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "question_id")
	    private Question question;

		public Answer(Long id, String content, int likes, String status, List<Comment> comments, User user,
				Question question) {
			super();
			this.id = id;
			this.content = content;
			this.likes = likes;
			this.status = status;
			this.comments = comments;
			this.user = user;
			this.question = question;
		}

		public Answer() {
			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getLikes() {
			return likes;
		}

		public void setLikes(int likes) {
			this.likes = likes;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<Comment> getComments() {
			return comments;
		}

		public void setComments(List<Comment> comments) {
			this.comments = comments;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Question getQuestion() {
			return question;
		}

		public void setQuestion(Question question) {
			this.question = question;
		}

	
		
	    
}


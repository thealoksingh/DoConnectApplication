package com.scoe.doconnect.model;
 
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(unique = true)
    @NotNull
	@Size(min=2, max=10)
    private String username;
 
    private String password;
 
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    private String role;
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions;
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Answer> answers;
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;
 
 
    // Constructors, getters, and setters
    public User() {
    }
 
    public User(Long id,String username, String password, String role) {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    	this.role = role;
    }
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getRole() {
        return role;
    }
 
    public void setRole(String role) {
        this.role = role;
    }
 
    public List<Question> getQuestions() {
        return questions;
    }
 
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
 
    public List<Answer> getAnswers() {
        return answers;
    }
 
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
 
    public List<Comment> getComments() {
        return comments;
    }
 
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
 
   

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}
    
    
}

package com.hcl.doconnect.model;
 
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
 
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(unique = true)
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
 
    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id"))
    @JsonIgnore
    private List<Answer> likedAnswers;
 
    // Constructors, getters, and setters
    public User() {
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
 
    public List<Answer> getLikedAnswers() {
        return likedAnswers;
    }
 
    public void setLikedAnswers(List<Answer> likedAnswers) {
        this.likedAnswers = likedAnswers;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role;
	}
    
    
}
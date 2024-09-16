package com.scoe.doconnect.service;

import java.util.List;

import com.scoe.doconnect.model.User;

public interface UserService   {
    User findById(Long id);
    User findByUsername(String username);

    void delete(User user);
    
    // Additional methods for user registration, login, and logout
    User registerUser(User user);
    User loginUser(String username);
    void logoutUser(String username);
	User registerAdmin(User user);
	List<User> getAllUsers();
	User updateUser(Long id, User user);
}
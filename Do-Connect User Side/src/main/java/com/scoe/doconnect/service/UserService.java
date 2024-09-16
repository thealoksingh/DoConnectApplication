package com.scoe.doconnect.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.scoe.doconnect.controller.ApiResponse;
import com.scoe.doconnect.dto.SendChatDTO;
import com.scoe.doconnect.dto.UserLoginDTO;
import com.scoe.doconnect.dto.UserRegisterDTO;
import com.scoe.doconnect.model.User;

public interface UserService   {
   
    
    // Additional methods for user registration, login, and logout
    User registerUser(UserRegisterDTO userRegisterDTO);
    ApiResponse loginUser(UserLoginDTO userLoginDTO);
   
	


	ApiResponse sendChat(SendChatDTO sendChatDTO);
	ApiResponse viewChatByUserId(Long userId);
	ApiResponse viewChatMenu(Long id);
	ApiResponse findAllMessagesByUserId(Long id);
	
}
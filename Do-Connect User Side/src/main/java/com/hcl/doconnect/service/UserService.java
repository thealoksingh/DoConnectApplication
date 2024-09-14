package com.hcl.doconnect.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.hcl.doconnect.controller.ApiResponse;
import com.hcl.doconnect.dto.SendChatDTO;
import com.hcl.doconnect.dto.UserLoginDTO;
import com.hcl.doconnect.dto.UserRegisterDTO;
import com.hcl.doconnect.model.User;

public interface UserService   {
   
    
    // Additional methods for user registration, login, and logout
    User registerUser(UserRegisterDTO userRegisterDTO);
    ApiResponse loginUser(UserLoginDTO userLoginDTO);
   
	


	ApiResponse sendChat(SendChatDTO sendChatDTO);
	ApiResponse viewChatByUserId(Long userId);
	ApiResponse viewChatMenu(Long id);
	ApiResponse findAllMessagesByUserId(Long id);
	
}
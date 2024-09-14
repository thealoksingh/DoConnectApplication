package com.hcl.doconnect;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.doconnect.controller.AdminController;
import com.hcl.doconnect.controller.ApiResponse;
import com.hcl.doconnect.dto.UpdateUserDTO;
import com.hcl.doconnect.exception.QuestionNotFoundException;
import com.hcl.doconnect.exception.UserNotFoundException;
import com.hcl.doconnect.model.Question;
import com.hcl.doconnect.model.User;
import com.hcl.doconnect.service.QuestionService;
import com.hcl.doconnect.service.UserService;
	 
@SpringBootTest
	 class DoConnectApplicationTests {
	 
	    @Mock
	    private UserService userService;  // Mocking the UserService
	    @Mock
	    private QuestionService questionService;  // Mocking the QuestionService
	 
	    @InjectMocks
	    private AdminController adminController;
	 
	  
	    private List<User> userList;
	    
	   
	 
	    @BeforeEach
	     void setup() {
	    	adminController.setAuthAdmin(new User(1L, "admin", "admin", "ADMIN"));
	        
	        userList = new ArrayList<>();
	        userList.add(new User(2L, "user1", "user1", "USER"));
	        userList.add(new User(3L, "user2", "user2", "USER"));
	    }
	 
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		@Test
	     void testGetAllUsers()  {
	        // Mocking authentication
	       
	        when(userService.getAllUsers()).thenReturn(userList);
	        
	        // Test the controller method
	        ResponseEntity response = adminController.getAllUsers();
	        
	        // Asserting the response
	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
	        assertEquals("User List Founded", ((ApiResponse<List<User>>) response.getBody()).getMessage());
	        assertEquals(userList, ((ApiResponse<List<User>>) response.getBody()).getT());
	    }
	    
	    @Test
	     void testGetUserById() throws  UserNotFoundException {
	        // Mocking authentication
	      
	        when(userService.findById(2L)).thenReturn(userList.get(1));
	        
	        // Test the controller method with an existing user ID
	        ResponseEntity<ApiResponse<User>> response = adminController.getUserById(2L);
	        
	        // Asserting the response
	        assertEquals(HttpStatus.FOUND, response.getStatusCode());
	        ApiResponse<User> apiResponse = (ApiResponse<User>) response.getBody();
	        assertEquals("User Details founded successfully", apiResponse.getMessage());
	        assertEquals(userList.get(1), apiResponse.getT());
	    }
	    
	    
	    
	    @Test
	     void testGetUserById_UserNotFound() throws  UserNotFoundException {
	        // Mocking authentication
	       
	        when(userService.findById(5L)).thenReturn(null);
	        
	       
	        assertThrows(UserNotFoundException.class, () -> {
	            adminController.getUserById(5L);
	        });

	    }
	    
	    @Test
	     void testDeleteUser_Success() throws  UserNotFoundException {
	        when(userService.findById(1L)).thenReturn(adminController.getAuthAdmin());
	        ResponseEntity<ApiResponse<User>> response = adminController.deleteUser(1L);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("User Details deleted successfully", response.getBody().getMessage());
	        verify(userService).delete(adminController.getAuthAdmin());
	    }
	 
	   
	 
	    @Test
	     void testDeleteUser_UserNotFound() throws  UserNotFoundException{
	        when(userService.findById(5L)).thenReturn(null);
	        Exception exception = assertThrows(UserNotFoundException.class, () -> {
	            adminController.deleteUser(5L);
	        });
	        assertEquals("User not found with given id = " + 5L, exception.getMessage());
	        verify(userService, never()).delete(any(User.class));
	    }
	    
	    @Test
	     void testUpdateUser_Success() throws UserNotFoundException {
	        when(userService.updateUser(eq(2L), any(User.class))).thenReturn(userList.get(1));
	        
	        ResponseEntity<ApiResponse<User>> response = adminController.updateUser(2L, new UpdateUserDTO(userList.get(1).getUsername(), userList.get(1).getPassword(), userList.get(1).getRole()));
	      
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(userList.get(1),  response.getBody().getT());
	        assertEquals("User updated Successfully",  response.getBody().getMessage());
	    }
	 
	   
	 
	    @Test
	     void testUpdateUser_NotFound() throws UserNotFoundException {
	        when(userService.updateUser(eq(5L), any(User.class))).thenReturn(null);
	 
	        assertThrows(UserNotFoundException.class, () -> {
	        	adminController.updateUser(5L, new UpdateUserDTO(userList.get(1).getUsername(), userList.get(1).getPassword(), userList.get(1).getRole()));	        });
	    }
	    
	    
//	    Testing on Question Part
	    
	    @Test
	     void testGetAllQuestions_Success() throws  QuestionNotFoundException {
	        
	    	 Question question = new Question();
	         question.setId(1L);
	    	
	    	when(questionService.findAll()).thenReturn(List.of(question));
	    
	        ResponseEntity<List<Question>> response = adminController.getAllQuestions();
	 
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertFalse(((List<Question>) response.getBody()).isEmpty());
	    }
	 
	   
	 
	    @Test
	     void testGetAllQuestions_NotFound() {
	        when(questionService.findAll()).thenReturn(Collections.emptyList());
	      
	        assertThrows(QuestionNotFoundException.class, () -> adminController.getAllQuestions());
	    }
	 
	    @Test
	     void testGetQuestionById_Success() throws  QuestionNotFoundException {
	    	Question question = new Question();
	         question.setId(1L);
	    	when(questionService.findById(1L)).thenReturn(question);
	        
	        ResponseEntity<ApiResponse<Question>> response = adminController.getQuestionById(1L);
	 
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(question, response.getBody().getT());
	    }
	 
	    
	 
	    @Test
	     void testGetQuestionById_NotFound() throws QuestionNotFoundException {
	        when(questionService.findById(2L)).thenReturn(null);
	       // Assuming a method to check authentication
	        assertThrows(QuestionNotFoundException.class, () -> adminController.getQuestionById(2L));
	    }
	    
	}
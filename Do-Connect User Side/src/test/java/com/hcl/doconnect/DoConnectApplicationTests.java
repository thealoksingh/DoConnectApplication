package com.hcl.doconnect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.when;
 
import java.util.ArrayList;

import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import com.scoe.doconnect.controller.ApiResponse;
import com.scoe.doconnect.controller.UserController;
import com.scoe.doconnect.dto.AnswerDTO;
import com.scoe.doconnect.dto.CommentDTO;
import com.scoe.doconnect.dto.QuestionDTO;
import com.scoe.doconnect.dto.QuestionDetailsDTO;
import com.scoe.doconnect.exception.AnswerNotFoundException;
import com.scoe.doconnect.exception.QuestionNotFoundException;
import com.scoe.doconnect.exception.UnauthorizedException;
import com.scoe.doconnect.model.Answer;
import com.scoe.doconnect.model.Comment;
import com.scoe.doconnect.model.Question;
import com.scoe.doconnect.model.User;
import com.scoe.doconnect.service.AnswerService;
import com.scoe.doconnect.service.QuestionService;
import com.scoe.doconnect.service.UserService;
import com.scoe.doconnect.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DoConnectApplicationTests {

    @Mock

    private JwtUtil jwtUtil;

    @Mock

    private UserService userService;

    @Mock

    private QuestionService questionService;

    @Mock

    private AnswerService answerService;

    @InjectMocks

    private UserController userController;


    private static final String UNAUTHORIZED_MESSAGE = "Unauthorized user trying to access data, You need to authorized first";

    @BeforeEach

    public void setUp() {

        userController.setAuthUser(new User());

    }

    @Test

    public void testAskQuestion_Successful() throws UnauthorizedException {

        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setContent("Test question");

        questionDTO.setTopic("Test Topic");

        when(questionService.saveQuestion(any(QuestionDTO.class))).thenReturn(null);

        ResponseEntity<?> response = userController.askQuestion(questionDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());

    }

    @Test

    public void testSearchQuestionsByContent() throws UnauthorizedException {

        String word = "test";

        List<Question> questions = new ArrayList<>();

        when(questionService.searchQuestions(anyString())).thenReturn(questions);

        ResponseEntity<?> response = userController.searchQuestionsByContent(word);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test

    public void testSearchQuestionsByTopic() throws UnauthorizedException {

        String word = "test";

        List<Question> questions = new ArrayList<>();

        when(questionService.searchQuestionsByTopic(anyString())).thenReturn(questions);

        ResponseEntity<?> response = userController.searchQuestionsByTopic(word);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test

    public void testGetAllApprovedQuestions_Success() throws UnauthorizedException {

        when(questionService.getAllApprovedQuestions()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = userController.getAllApprovedQuestions();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test

    public void testGetQuestionById_Successful() throws QuestionNotFoundException, UnauthorizedException {

        Long id = 1L;

        Question question = new Question();

        question.setStatus("APPROVED");

        when(questionService.findById(id)).thenReturn(question);

        ResponseEntity<?> response = userController.getQuestionById(id);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(question, response.getBody());

    }

    @Test

    public void testGetQuestionDetails() throws QuestionNotFoundException, UnauthorizedException {

        Long id = 1L;

        QuestionDetailsDTO questionDetails = new QuestionDetailsDTO();

        when(questionService.getQuestionDetails(id)).thenReturn(questionDetails);

        ResponseEntity<?> response = userController.getQuestionDetails(id);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(questionDetails, ((ApiResponse<?>)response.getBody()).getT());

    }

    @Test

    public void testAddAnswer() throws QuestionNotFoundException, UnauthorizedException {

        AnswerDTO answerDTO = new AnswerDTO();

        answerDTO.setContent("Test answer");

        ApiResponse<Object> apiResponse = new ApiResponse<>("Answer added", null);

        when(answerService.save(answerDTO)).thenReturn(apiResponse);

        ApiResponse<Object> response = userController.addAnswer(answerDTO);

        assertEquals("Answer added", response.getMessage());

    }

    @Test

    public void testLikeAnswerUnauthorized() {

        // Setup

        userController.setAuthUser(null);  // No user logged in

        // Execution

        Exception exception = assertThrows(UnauthorizedException.class, () ->

            userController.likeAnswer(1L)

        );

        // Verify

        assertEquals(UNAUTHORIZED_MESSAGE, exception.getMessage());

    }

    @SuppressWarnings("unchecked")
	@Test

    public void testLikeAnswerNotFound() throws UnauthorizedException {

        // Setup

        @SuppressWarnings("rawtypes")
		ApiResponse response = new ApiResponse<>("Answer not found",null);

        when(answerService.likeAnswerById(1L)).thenReturn(response);

        // Execution

        Exception exception = assertThrows(AnswerNotFoundException.class, () ->

            userController.likeAnswer(1L)

        );

        // Verify

        assertEquals("Answer not found", exception.getMessage());

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test

    public void testLikeAnswerSuccess() throws UnauthorizedException, AnswerNotFoundException {

        // Setup

        ApiResponse response = new ApiResponse<>("Answer liked successfully", new Answer());

        when(answerService.likeAnswerById(1L)).thenReturn(response);

        // Execution

        ResponseEntity<?> result = userController.likeAnswer(1L);

        // Verify

        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        assertEquals(response, result.getBody());

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test

    public void testLikeAnswerBadRequest() throws UnauthorizedException, AnswerNotFoundException {

        // Setup

        ApiResponse response = new ApiResponse<>("Failure", new Answer());

        when(answerService.likeAnswerById(1L)).thenReturn(response);

        // Execution

        ResponseEntity<ApiResponse> result = userController.likeAnswer(1L);

        // Verify

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        assertEquals(response, result.getBody());

    }
 
    @Test

    public void testAddCommentToAnswer() throws AnswerNotFoundException, UnauthorizedException {

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setContent("Test comment");

        ApiResponse<Comment> apiResponse = new ApiResponse<>("Comment added", new Comment());

        when(answerService.addCommentToAnswer(commentDTO)).thenReturn(apiResponse);

        ApiResponse<Comment> response = userController.addCommentToAnswer(commentDTO);

        assertEquals("Comment added", response.getMessage());

        assertNotNull(response.getT());

    }

    // Continue adding more test methods for other endpoints

}
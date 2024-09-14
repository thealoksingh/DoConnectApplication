package com.hcl.doconnect.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.doconnect.dto.AnswerDTO;
import com.hcl.doconnect.dto.CommentDTO;
import com.hcl.doconnect.dto.QuestionDTO;
import com.hcl.doconnect.dto.QuestionDetailsDTO;
import com.hcl.doconnect.dto.SendChatDTO;
import com.hcl.doconnect.dto.UserLoginDTO;
import com.hcl.doconnect.dto.UserRegisterDTO;
import com.hcl.doconnect.exception.AnswerNotFoundException;
import com.hcl.doconnect.exception.ChatNotFoundException;
import com.hcl.doconnect.exception.FieldValidationFailedException;
import com.hcl.doconnect.exception.QuestionNotFoundException;
import com.hcl.doconnect.exception.UnauthorizedException;
import com.hcl.doconnect.model.Comment;
import com.hcl.doconnect.model.Question;
import com.hcl.doconnect.model.User;
import com.hcl.doconnect.service.AnswerService;
import com.hcl.doconnect.service.QuestionService;
import com.hcl.doconnect.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	private static final String APPROVED = "APPROVED";

	private User authUser;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String UNAUTHORIZED_MESSAGE = "Unauthorized user trying to access data, You need to authorized first";

	public User getAuthUser() {
		return authUser;
	}

	public void setAuthUser(User authUser) {
		this.authUser = authUser;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO,
			BindingResult bindingResult) throws FieldValidationFailedException {
		if (bindingResult.hasErrors()) {
			throw new FieldValidationFailedException(bindingResult);
		}

		var user = userService.registerUser(userRegisterDTO);
		if (user != null) {
			logger.info("User with username = {} Registered Successfully", user.getUsername());
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(String.format("User with username = %s registered Succesfully", user.getUsername()));
		}
		logger.info("Username = {} already existed.", userRegisterDTO.getUsername());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("User already existed with the username = " + userRegisterDTO.getUsername());
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<User>> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO,
			BindingResult bindingResult) throws FieldValidationFailedException {
		if (bindingResult.hasErrors()) {
			throw new FieldValidationFailedException(bindingResult);
		}

		ApiResponse<User> response = userService.loginUser(userLoginDTO);
		if (response.getT() != null) {
			authUser = new User();
			logger.info("User logged-In successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		logger.info("Bad credential provided in user login");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}

//	Ask Question
	@PostMapping("/question")
	public ResponseEntity<String> askQuestion(@RequestBody QuestionDTO questionDTO) throws UnauthorizedException {

		var question = questionService.saveQuestion(questionDTO);
		if (question == null) {
			logger.info("Question Posted successfully but pending with Admin.");
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Question Posted successfully but pending with Admin");
		} else {
			logger.info("Question with same content already exists.");
			return ResponseEntity.status(HttpStatus.FOUND).body("Question with same content already exists.");
		}
	}

//    Search Question based on String.
	@GetMapping("/questions/searchByContent/{word}")
	public ResponseEntity<?> searchQuestionsByContent(@PathVariable String word) throws UnauthorizedException {

		List<Question> foundQuestions = questionService.searchQuestions(word);
		if (foundQuestions.isEmpty()) {
			logger.info("No Question Found With Matching Word .");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Question Found With Matching Word " + word);
		} else {
			logger.info("Question Found Successfully By Content .");
			return ResponseEntity.status(HttpStatus.CREATED).body(foundQuestions);
		}
	}

//    Search Question based on String.
	@GetMapping("/questions/searchByTopic/{word}")
	public ResponseEntity<?> searchQuestionsByTopic(@PathVariable String word) throws UnauthorizedException {

		List<Question> foundQuestions = questionService.searchQuestionsByTopic(word);
		if (foundQuestions.size() == 0) {
			logger.info("No Question Found With Matching Word .");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Question Found With Matching Word " + word);
		} else {
			logger.info("Question Found Successfully By Topic .");
			return ResponseEntity.status(HttpStatus.CREATED).body(foundQuestions);
		}
	}

//    view All approved questions
	@GetMapping("/questions/viewall")
	public ResponseEntity<?> getAllApprovedQuestions()  {

		List<Question> approvedQuestions = questionService.getAllApprovedQuestions();
		if (approvedQuestions == null || approvedQuestions.isEmpty()) {
			logger.info("No Approved Question At this time.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Approved Question At this time.");
		} else {
			logger.info("Approved Questions Founded.");
			return ResponseEntity.status(HttpStatus.CREATED).body(approvedQuestions);
		}
	}

//	get question by ID
	@GetMapping("/questions/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable Long id)
			throws QuestionNotFoundException {

		Question question = questionService.findById(id);
		if (question != null && question.getStatus().equals(APPROVED)) {
			logger.info("Successfully Got the Question  by the given id .");
			return ResponseEntity.status(HttpStatus.CREATED).body(question);
		} else {
			logger.info("Question not found from given id = {}",id);
			throw new QuestionNotFoundException("Question not found with given id = " + id);
		}
	}

//	Get FUll details of Question with ID

	@GetMapping("/question/{id}/details")
	public ResponseEntity<?> getQuestionDetails(@PathVariable long id)
			throws QuestionNotFoundException {

		QuestionDetailsDTO questionDetailsDTO = questionService.getQuestionDetails(id);
		if (questionDetailsDTO != null) {
			logger.info("Questions details founded with id = {}", id);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse<>("Questions details founded", questionDetailsDTO));
		} else {
			logger.info("Question not found with given id = {}", id);
			throw new QuestionNotFoundException("Question not found with given id = " + id);
		}
	}

//	Add answer to question with id
	@PostMapping("/add/answer")
	public ApiResponse<Object> addAnswer(@RequestBody AnswerDTO answerDTO)
			throws QuestionNotFoundException, UnauthorizedException {

		logger.info("Successfully Added answer to Question with the help of id.");
		return answerService.save(answerDTO);

	}

//	like answer with answer id
	@SuppressWarnings("rawtypes")
	@GetMapping("/like/answer/{answerId}")
	public ResponseEntity<ApiResponse> likeAnswer(@PathVariable Long answerId)
			throws  AnswerNotFoundException {

		logger.info("Successfully Liked The Answer with the help of answer id");
		ApiResponse response = answerService.likeAnswerById(answerId);
		if (response.getT() == null) {
			throw new AnswerNotFoundException(response.getMessage());
		} else if (response.getMessage().equals("Answer liked successfully")) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	// Comment on Answer with answer ID
	@PostMapping("/answer/comment")
	public ApiResponse<Comment> addCommentToAnswer(@RequestBody CommentDTO commentDTO)
			throws AnswerNotFoundException {

		logger.info("Successfully Commented on Answer with the help of answer id");
		return answerService.addCommentToAnswer(commentDTO);

	}

//	Chat API for User

//	Send Message to user by id
	@SuppressWarnings("rawtypes")
	@PostMapping("/user/message/send")
	public ApiResponse sendChat(@RequestBody SendChatDTO sendChatDTO) throws UnauthorizedException {

		logger.info("Message sended Successfully to user with id = {}", sendChatDTO.getToUserId());
		return userService.sendChat(sendChatDTO);

	}

//  Find recent chat Menu of user 

	@SuppressWarnings("rawtypes")
	@GetMapping("/user/recentChatMenu")
	public ResponseEntity<ApiResponse> viewChatMenu() throws UnauthorizedException, ChatNotFoundException {
		ApiResponse response = userService.viewChatMenu(authUser.getId());
		if (response.getMessage().equals("No message found at this time")) {
			throw new ChatNotFoundException("No message found at this time");
		}
		logger.info("Successfully Found The Chat Menu for authorized User");
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

//	find all chats  with user id

	@SuppressWarnings("rawtypes")
	@GetMapping("/chat/{userId}")
	public ResponseEntity findAllMessagesByUserId(@PathVariable Long userId)
			throws UnauthorizedException, ChatNotFoundException {

		ApiResponse response = userService.findAllMessagesByUserId(userId);
		if (response.getT() == null) {
			logger.info(response.getMessage());
			throw new ChatNotFoundException(response.getMessage());
		}
		logger.info("Successfully Found Message with the help of user id = {}", userId);
		return ResponseEntity.status(HttpStatus.OK).body(response.getT());
	}

}

package com.scoe.doconnect.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scoe.doconnect.dto.AnswerDTO;
import com.scoe.doconnect.dto.CommentDTO;
import com.scoe.doconnect.dto.QuestionDTO;
import com.scoe.doconnect.dto.QuestionDetailsDTO;
import com.scoe.doconnect.dto.SendChatDTO;
import com.scoe.doconnect.dto.UpdateQuestionDTO;
import com.scoe.doconnect.dto.UpdateUserDTO;
import com.scoe.doconnect.dto.UserLoginDTO;
import com.scoe.doconnect.dto.UserRegisterDTO;
import com.scoe.doconnect.exception.AnswerNotFoundException;
import com.scoe.doconnect.exception.FieldValidationFailedException;
import com.scoe.doconnect.exception.NotificationNotFoundException;
import com.scoe.doconnect.exception.QuestionNotFoundException;
import com.scoe.doconnect.exception.UserNotFoundException;
import com.scoe.doconnect.model.Answer;
import com.scoe.doconnect.model.Chat;
import com.scoe.doconnect.model.Comment;
import com.scoe.doconnect.model.Notification;
import com.scoe.doconnect.model.Question;
import com.scoe.doconnect.model.User;
import com.scoe.doconnect.repository.NotificationRepository;
import com.scoe.doconnect.repository.QuestionRepository;
import com.scoe.doconnect.service.AnswerService;
import com.scoe.doconnect.service.ChatService;
import com.scoe.doconnect.service.CommentService;
import com.scoe.doconnect.service.NotificationService;
import com.scoe.doconnect.service.QuestionService;
import com.scoe.doconnect.service.UserService;
import com.scoe.doconnect.util.JwtUtil;

import springfox.documentation.annotations.ApiIgnore; 



@RestController
@RequestMapping("/api/admin")
public class AdminController {
	// Autowire UserService
	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	private  User authAdmin = new User();

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private CommentService commentService;

	@Autowired
	private NotificationRepository notificationRepository;

	private static final String APPROVED = "APPROVED";
	private static final String RESOLVED = "RESOLVED";
	private static final String UNSEEN = "UNSEEN";
	private static final String SEEN = "SEEN";

	 


	private User authUser = new User();

	@Autowired
	private ChatService chatService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	  
	
	  
	public User getAuthUser() {
		return authUser;
	}
	public void setAuthUser(User authUser) {
		this.authUser = authUser;
	}

    public User getAuthAdmin() {
		return authAdmin;
	}

	public void setAuthAdmin(User authAdmin) {
		this.authAdmin = authAdmin;
	}

	//	Register Admin Api
	@PostMapping("/register")
	public ResponseEntity<Object> registerAdmin(@Valid @RequestBody UserRegisterDTO userRegisterDTO,
			BindingResult bindingResult) throws FieldValidationFailedException {
		if (bindingResult.hasErrors()) {
			throw new FieldValidationFailedException(bindingResult);
		}
		var user = new User();
		user.setUsername(userRegisterDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

		var registeredAdmin = userService.registerAdmin(user);
		if (registeredAdmin == null) {
 
			logger.info("Admin with username = {} already existed.",user.getUsername());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("Admin with username = %s already existed.",user.getUsername()));
		}

		logger.info("Admin with username = {} Registered Successfully", user.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Admin registered successfully", registeredAdmin));
	}

	@PostMapping("/user/create")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult) throws FieldValidationFailedException {
		if (bindingResult.hasErrors()) {
			
			throw new FieldValidationFailedException(bindingResult);
			
		}

		if (userService.findByUsername(userRegisterDTO.getUsername()) != null) {
			
			
			logger.info("User with username = {} already existed.",userRegisterDTO.getUsername());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("User with username = " + userRegisterDTO.getUsername() + " already existed.");

		}
		var user = new User(userRegisterDTO.getUsername(), passwordEncoder.encode(userRegisterDTO.getPassword()),
				"USER");
		var registeredUser = userService.registerUser(user);
		
		logger.info("User registered successfully with username = {}", registeredUser.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(String.format("User registered successfully with username = %s",registeredUser.getUsername()));
	
	}

//	Login Admin Api
	@PostMapping("/login")
	public ResponseEntity<Object> loginAdmin(@RequestBody UserLoginDTO userRegisterDTO) throws UserNotFoundException {


		var user = new User();
		user.setUsername(userRegisterDTO.getUsername());
		user.setPassword(userRegisterDTO.getPassword());
		var loggedInAdmin = userService.loginUser(user.getUsername());

		if (loggedInAdmin != null && loggedInAdmin.getRole().equals("ADMIN")
				&& passwordEncoder.matches(userRegisterDTO.getPassword(), loggedInAdmin.getPassword())) {

			setAuthAdmin(loggedInAdmin);

			String token = JwtUtil.generateToken(getAuthAdmin().getUsername());
			logger.info("User with username = {} logged-In successfully.",getAuthAdmin().getUsername());
			logger.info("Jwt Token generated successfully. Token : {}",token);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
					"Token Generated successfully\n" + "The token is : Bearer " + token, loggedInAdmin));
		} else {

			logger.info("Bad credential provided");
			throw new UserNotFoundException("Bad credential Provided");
		}

	}

	
//	CRUD Operation On User
//	 Get all User
	@GetMapping("/users")
	public ResponseEntity<Object> getAllUsers() {

			List<User> users = userService.getAllUsers();
			logger.info("User Details List Founded");
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("User List Founded", users));


	}

//	 Get user By ID
	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) throws  UserNotFoundException {

		var user = userService.findById(id);
		if (user != null) {
			logger.info("User Details Founded Succesfully.");
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ApiResponse<>("User Details founded successfully", user));

		} else {
			logger.info("User Details Not Found." );
			throw new UserNotFoundException("User not found with given id = " + id);
		}

	}

//		Delete User by ID
	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) throws  UserNotFoundException {



		var user = userService.findById(id);
		if (user != null) {
			userService.delete(user);
			logger.info("User Details deleted Succesfully.");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("User Details deleted successfully", user));
		} else {
			throw new UserNotFoundException("User not found with given id = " + id);

		}
	}

//		Update User By ID
	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO)
			throws UserNotFoundException {
		
		var updatedUser = userService.updateUser(id, new User(updateUserDTO.getUsername(),updateUserDTO.getPassword(),updateUserDTO.getRole()));
		if (updatedUser != null) {
			logger.info("User Details Updated Succesfully.");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("User updated Successfully", updatedUser));

		} else {
			throw new UserNotFoundException("User not found with id = " + id);
		}
	}

//		CRUD Operations on Questions.

//		Get All Questions
	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllQuestions() throws  QuestionNotFoundException {

		List<Question> questions = questionService.findAll();

		if (questions.isEmpty()) {
			throw new QuestionNotFoundException("No question are available at this time");
		}
		logger.info("Questions Details Founded Succesfully.");
		return ResponseEntity.status(HttpStatus.OK).body(questions);
	}

//	get question by ID
	@GetMapping("/questions/{id}")
	public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable Long id)
			throws QuestionNotFoundException {

		var question = questionService.findById(id);
		if (question != null) {
			logger.info("Question details founded successfully with id = {}", id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("Question details with id = "+id+" founded successfully", question));
		} else {
			throw new QuestionNotFoundException("Question not found with the given id = " + id);
		}
	}

//  view All approved questions
	@GetMapping("/questions/approved")
	public ResponseEntity<Object> getAllApprovedQuestions() throws  QuestionNotFoundException {

		List<Question> approvedQuestions = questionService.getAllApprovedQuestions();
		if (approvedQuestions.isEmpty())
			throw new QuestionNotFoundException("No Approved Question are available at this time. ");

		logger.info("Approved Questions Founded Successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(approvedQuestions);
	}

//  view All Pending questions
	@GetMapping("/questions/pending")
	public ResponseEntity<Object> getAllPendingQuestions() throws  QuestionNotFoundException {

		List<Question> pendingQuestions = questionService.getAllPendingQuestions();
		if (pendingQuestions.isEmpty()) {
			throw new QuestionNotFoundException("No pending questions are available at this time");

		}

		logger.info("Pending Questions Founded Successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(pendingQuestions);
	}

//  view All Resolved questions
	@GetMapping("/questions/resolved")
	public ResponseEntity<Object> getAllResolvedQuestions() throws  QuestionNotFoundException {
		
		List<Question> resolvedQuestions = questionService.getAllResolvedQuestions();
		if (resolvedQuestions.isEmpty())
			throw new QuestionNotFoundException("No resolved questions are available at this time");

		logger.info("Resolved Questions Founded Successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(resolvedQuestions);
	}

//		create question
	@PostMapping("/questions")
	public ResponseEntity<Object> createQuestion(@RequestBody QuestionDTO questionDTO)   {

		var question = new Question(questionDTO.getTopic(), questionDTO.getContent(), getAuthAdmin(), APPROVED);
		var createdQuestion = questionService.saveQuestion(question);
		logger.info(" New Question Created Successfully.");
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
	}

//	    Update question by id
	@PutMapping("/questions/{id}")
	public ResponseEntity<Object> updateQuestion(@PathVariable Long id, @RequestBody UpdateQuestionDTO updateQuestionDTO)
			throws QuestionNotFoundException {

		var question = new Question();
		question.setTopic(updateQuestionDTO.getTopic());
		question.setContent(updateQuestionDTO.getContent());
		question.setStatus(updateQuestionDTO.getStatus());
		var updatedQuestion = questionService.updateQuestion(id, question);
		if (updatedQuestion != null) {
			logger.info("Question with id = {} updated Successfully.", id);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedQuestion);
		} else {
			throw new QuestionNotFoundException(String.format("Question Not Found With Given id = %d" ,id));
		}
	}

//	    Delete Question by ID
	@DeleteMapping("/questions/{id}")
	public ResponseEntity<Object> deleteQuestion(@PathVariable Long id)
			throws QuestionNotFoundException {

		questionService.deleteQuestion(id); // QuestionNotFoundException throws if question with id is not existed.
		logger.info("Question with id = {} deleted Successfully.", id);
		return ResponseEntity.status(HttpStatus.OK).body("Question deleted Successfully with id = "+id);
	}

//		Add answer to question with id
	@PostMapping("/answers")
	public ResponseEntity<Object> addAnswer(@RequestBody AnswerDTO answerDTO)
			throws QuestionNotFoundException {

		var question = questionService.findById(answerDTO.getQuestionId());
		if (question != null) {
			var answer = new Answer();
			answer.setContent(answerDTO.getContent());
			answer.setStatus("APPROVED");
			answer.setUser(getAuthAdmin());
			answer.setQuestion(question);

			var savedAnswer = answerService.save(answer);
			logger.info("Answer posted successfully by Admin");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("Answer posted successfully by Admin", savedAnswer));
		}

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("This service is available at this time. Try again Later");

	}

//	    Approve Question By ID
	// Method to approve a question
	@PutMapping("/questions/approve/{id}")
	public ResponseEntity<Object> approveQuestion(@PathVariable Long id)
			throws QuestionNotFoundException {

		boolean approved = questionService.approveQuestion(id);
		if (approved) {
			return ResponseEntity.ok().body("Question approved successfully");
		} else {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body("This service is unavailable at this time. Try again Later");
		}
	}

	// Method to close discussion thread for a question and update status
	@PutMapping("/questions/closediscussion/{id}")
	public ResponseEntity<Object> closeDiscussion(@PathVariable Long id)
			throws QuestionNotFoundException {

		boolean closed = questionService.closeDiscussion(id);
		if (closed) {
			logger.info("Discussion closed successfully for the Question with id = {}", id);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(String.format(
					"Discussion closed successfully for the Question with id = %d", id), questionService.findById(id)));
		} else {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body("This service is available at this time. Try again Later");
		}
	}

//  view All Pending Answer
	@GetMapping("/answers/pending")
	public ResponseEntity<Object> getAllPendingAnswer() throws AnswerNotFoundException {

		List<Answer> approvedQuestions = answerService.getAllPendingAnswer();
		if (approvedQuestions.isEmpty()) {

			throw new AnswerNotFoundException("No Pending answer are available at this time.");
		}

		logger.info("Pending Answer Founded Successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(approvedQuestions);
	}

	// Get Answers with Question Id
	@GetMapping("/answers/questions/{questionId}")
	public ResponseEntity<Object> viewAnswersWithQuestionId(@PathVariable Long questionId)
			throws  AnswerNotFoundException {
		List<Answer> answers = answerService.viewAnswersWithQuestionId(questionId);
		if (answers != null && !answers.isEmpty()) {
			logger.info("Answers of Question with id = {} founded successfully.", questionId);
			return ResponseEntity.status(HttpStatus.OK).body(answers);
		} else {
			throw new AnswerNotFoundException(
					"Currently no answers are available for the Question with id = " + questionId);
		}

	}

//  view All Approved Answer
	@GetMapping("/answers/approved")
	public ResponseEntity<Object> getAllApprovedAnswer() throws  AnswerNotFoundException {

		List<Answer> approvedQuestions = answerService.getAllApprovedAnswer();
		if (approvedQuestions.isEmpty())
			throw new AnswerNotFoundException("No approved questions are available at this time");

		logger.info("Pending Answer Founded Successfully.");
		return ResponseEntity.status(HttpStatus.FOUND).body(approvedQuestions);
	}

	// Method to approve an answer
	@PutMapping("/answers/approve/{id}")
	public ResponseEntity<Object> approveAnswer(@PathVariable Long id)
			throws  AnswerNotFoundException {

		boolean approved = answerService.approveAnswer(id);
		if (approved) {
			logger.info("Answer with id = {} approved successfully.",id);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<Object>("Answer approved successfully", answerService.findById(id)));
		} else {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body("Currently this service is unavailble try after some time.");
		}
	}

	// Method to delete an answer
	@DeleteMapping("/answers/delete/{id}")
	public ResponseEntity<Object> deleteAnswer(@PathVariable Long id) throws AnswerNotFoundException  {

		boolean deleted = answerService.deleteAnswer(id);
		if (deleted) {
			logger.info("Answer deleted successfully with given id = {}" , id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Answer deleted successfully with given id = %d", id));
		} 
		throw new AnswerNotFoundException(String.format("Answer with id = %d is not exist",id));
	}

//	Get FUll details of Question with ID

	@GetMapping("/questions/details/{id}")
	public ResponseEntity<Object> getQuestionDetails(@PathVariable long id)
			throws QuestionNotFoundException {


		var questionDetailsDTO = questionService.getQuestionDetails(id);
		if (questionDetailsDTO != null) {	
			logger.info("Questions details founded with id = {}.",id);
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ApiResponse<>("Questions details founded.", questionDetailsDTO));
		}
		throw new QuestionNotFoundException(String.format("Questions Details not found with id = %d",id));
	}

//	API for Notifications

//	get All Notifications
	@GetMapping("/notifications")
	public ResponseEntity<Object> getAllNotifications() throws  NotificationNotFoundException {

		List<Notification> notifications = notificationService.getAllNotification();
		if (notifications.isEmpty()) {
			throw new NotificationNotFoundException("Currently no notifications are available.");
		}
		logger.info("All notifications details founded.");
		return ResponseEntity.status(HttpStatus.OK).body(notifications);
	}

//	get Unread Notifications
	@GetMapping("/notifications/unread")
	public ResponseEntity<Object> getAllUnreadNotifications() throws  NotificationNotFoundException {

		List<Notification> notifications = notificationService.getAllUnreadNotifications();
		if (notifications.isEmpty()) {
			throw new NotificationNotFoundException("Currently no notifications are available.");
		}
		logger.info("All Unread notifications details founded.");
		return ResponseEntity.status(HttpStatus.OK).body(notifications);
	}

//	Read/Get Notifications By Id
	@GetMapping("/notifications/{id}")
	public ResponseEntity<Object> getNotificationsById(@PathVariable Long id)
			throws  NotificationNotFoundException {


		var notification = notificationService.getNotificationsById(id); // throws
																					// NotificationNotFoundException if
																					// not exists
		if (notification != null) {
			if (notification.getStatus().equals(UNSEEN)) {
				notification.setStatus(SEEN);
				notificationService.markedNotificationSeen(notification);
			}
			logger.info("Notifications details with id = {} founded.",id);
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ApiResponse<>("Notification details founded ", notification));
		}
		throw new NotificationNotFoundException(String.format("Currently no notifications are available with id = %d",id));

	}

	// Create Comment on Answer with answer ID
	@PostMapping("/answers/comment")
	public ResponseEntity<Object> addCommentToAnswerWithId(@RequestBody CommentDTO commentDTO)
			throws AnswerNotFoundException {

		var answer = answerService.findById(commentDTO.getAnswerId());

		if (answer != null && answer.getStatus().equals(APPROVED)) {
			var savedComment = commentService.addComment(commentDTO.getAnswerId(), commentDTO, authUser);

			if (savedComment == null) {
				logger.info("Either the answer is not available or The discussion is closed on this Question with id = {}",answer.getQuestion().getId());
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body("Either the answer is not available or The discussion is closed on this Question");
			}
			logger.info("Comment added successfully to answer with id = {}" , commentDTO.getAnswerId());
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
					"Comment added successfully to answer with id = " + commentDTO.getAnswerId(), savedComment));

		} else {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something gone Wrong. Try again after sometime.");
		}
	}

//		View Comment With Answer Id
	@GetMapping("/answer/comments/{answerId}")
	public ResponseEntity<ApiResponse<List<Comment>>> viewCommentsWithAnswerId(@PathVariable Long answerId)
			throws  AnswerNotFoundException {

		List<Comment> comments = commentService.getCommentWithAnswerId(answerId);
		if (!comments.isEmpty()) {
			logger.info("Comment founded successfully to answer with id = {}",answerId);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("Comments Details founded", comments));
		}
		logger.info("Comments with answer id = {} are not available at this moment.",answerId);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Comments are not available at this moment.",comments) );
	}

//	API for User MicroServices

//	User Login API

	@PostMapping("/user/register")
	@ApiIgnore
	public User registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {

		if (userService.findByUsername(userRegisterDTO.getUsername()) != null) {
			return null;

		}
		var user = new User(userRegisterDTO.getUsername(), passwordEncoder.encode(userRegisterDTO.getPassword()),
				"USER");
		return userService.registerUser(user);

	}

	@ApiIgnore
	@PostMapping("/user/login")
	public ApiResponse<User> loginUser(@RequestBody UserLoginDTO userDTO) {

		var loggedInUser = userService.loginUser(userDTO.getUsername());

		String token = null;
		if (loggedInUser != null && loggedInUser.getRole().equals("USER")
				&& passwordEncoder.matches(userDTO.getPassword(), loggedInUser.getPassword())) {
			authUser = loggedInUser;
			token = JwtUtil.generateToken(authUser.getUsername());

			return new ApiResponse<>("Token generated successfully. Token = " + token, authUser);
		}

		return new ApiResponse<>("Bad credential provided", null);

	}

//	Ask Question by User
	@ApiIgnore
	@PostMapping("/user/question/ask")
	public Question askQuestion(@RequestBody QuestionDTO questionDTO) {

		var question = new Question();

		question.setUser(authUser);
		question.setTopic(questionDTO.getTopic());
		question.setContent(questionDTO.getContent());
		question.setStatus("PENDING");

		var checkQuestion = questionService.findByContent(question);

		if (checkQuestion == null) {

			var savedQuestion = questionService.saveQuestion(question);

			var notification = new Notification("User with id = " + authUser.getId()
					+ " added the question with id = " + savedQuestion.getId() + " but pending with you.", UNSEEN,
					LocalDateTime.now());
			notificationRepository.save(notification);
			return null;
		}
		
		return question;
	}

//  Search Question based on String.
	@ApiIgnore
	@GetMapping("/user/questions/search/{word}")
	public List<Question> searchQuestions(@PathVariable String word) {

		return questionService.searchQuestions(word);

		

	}

//  Search Question based on Topic.
	@ApiIgnore
	@GetMapping("/user/questions/searchByTopic/{word}")
	public List<Question> searchQuestionsByTopic(@PathVariable String word) {

		return questionService.searchQuestionsByTopic(word);

		

	}

//  view All approved questions
	@GetMapping("/user/questions/viewall")
	@ApiIgnore
	public List<Question> viewAllApprovedQuestions() {

		return questionService.getAllApprovedQuestions();

		
	}

//	Get FUll details of Question with ID

	@GetMapping("/user/question/{id}/details")
	public QuestionDetailsDTO getQuestionDetailsForUser(@PathVariable long id) throws QuestionNotFoundException {

		return questionService.getQuestionDetails(id);
	}

//	get question by ID
	@GetMapping("/user/question/{id}")
	@ApiIgnore
	public Question viewQuestionById(@PathVariable Long id) throws QuestionNotFoundException {

		boolean question = questionService.existById(id);
		if(question) {
			return questionService.findById(id);
		}
		return null;
	}

//	Add answer to question with id
	@PostMapping("/user/add/answer")	
	@ApiIgnore
	public ApiResponse<Object> addAnswerByUser(@RequestBody AnswerDTO answerDTO)  {

		Optional<Question> optionalQuestion = questionRepository.findById(answerDTO.getQuestionId());
		if (optionalQuestion.isPresent() && optionalQuestion.get().getStatus().equals(RESOLVED)) {
			return new ApiResponse<>("Discussion Closed with Question id = " + answerDTO.getQuestionId(),
					optionalQuestion.get());
		} else if (optionalQuestion.isPresent() && optionalQuestion.get().getStatus().equals(APPROVED)) {
			var answer = new Answer();
			answer.setContent(answerDTO.getContent());
			answer.setStatus("PENDING"); // Assuming all answers are pending approval by admin
			answer.setUser(authUser);
			answer.setQuestion(optionalQuestion.get());

			var savedAnswer = answerService.save(answer);
			var notification = new Notification("User with id = " + authUser.getId()
					+ " added the Answer with id = " + savedAnswer.getId() + " but pending with you.", UNSEEN,
					LocalDateTime.now());
			notificationRepository.save(notification);
			return new ApiResponse<>("Answer posted successfully but pending with Admin", savedAnswer);
		}
		return new ApiResponse<>("Questions not found with given Id = " + answerDTO.getQuestionId(), null);

	}

//	like answer with answer id
//	@ApiIgnore
	@GetMapping("/user/like/answer/{answerId}")
	public ApiResponse<Answer> likeAnswer(@PathVariable Long answerId) throws AnswerNotFoundException {

		var answer = answerService.findById(answerId);
		
		if (answer != null && answer.getStatus().equals(APPROVED)) {

			// Implemented logic to like the answer
			// increment the like count for the answer
			boolean checked = answerService.checkLikedByUser(answer.getId(), authUser.getId());
			if (!checked) {
				answer.setLikes(answer.getLikes() + 1);
				// Save the updated answer
				var updatedAnswer = answerService.save(answer);
				answerService.saveLikeByUser(answer.getId(), authUser.getId());
			
				return new ApiResponse<>("Answer liked successfully", updatedAnswer);
			}
			return new ApiResponse<>("You Already Liked this Answer ", answer);

		}
		return new ApiResponse<>("Answer not found with id = " + answerId, null);

	}

	// Comment on Answer with answer ID
	@PostMapping("/user/answer/comment")
	@ApiIgnore
	public ApiResponse<Comment> addCommentToAnswer(@RequestBody CommentDTO commentDTO) throws AnswerNotFoundException {

		var answer = answerService.findById(commentDTO.getAnswerId());

		if (answer != null && answer.getStatus().equals(APPROVED)) {
			var savedComment = commentService.addComment(commentDTO.getAnswerId(), commentDTO, authUser);
			return new ApiResponse<>("Comment added successfully", savedComment);

		}
		return new ApiResponse<>("Answer not found with id = " + commentDTO.getAnswerId(), null);
	}

//	Chat API for User

	@PostMapping("/user/chat/send")
	@ApiIgnore
	public ApiResponse<Chat> sendChat(@RequestBody SendChatDTO sendChatDTO) {
		var fromUser = authUser;
		var toUser = userService.findById(sendChatDTO.getToUserId());
		if (fromUser != null && toUser != null) {
			var chat = new Chat();

			chat.setFrom(fromUser);
			chat.setTo(toUser);
			chat.setMessage(sendChatDTO.getMessage());
			chat.setTime(LocalDateTime.now());
			chat.setStatus(UNSEEN);
			chatService.saveChat(chat);
			return new ApiResponse<>("Chat sended Successfully", chat);
		}

		return new ApiResponse<>("User with id = " + sendChatDTO.getToUserId() + " not found", null);
	}

	
	@GetMapping("/chat/{userId}")
	@ApiIgnore
	public ApiResponse<List<Chat>> findAllMessagesByUserId(@PathVariable Long userId) {
		List<Chat> chats = chatService.findMessagesBetweenUsers(authUser.getId(), userId);
		logger.info(chats.toString());
		if (!chats.isEmpty()) {
			return new ApiResponse<>("Message founded Successfully",chats);
		}
		return new ApiResponse<>("Message Not Found with user id = "+userId ,null);

	}

	@GetMapping("/user/recentChat/menu")
	@ApiIgnore
	public ApiResponse<List<User>> viewChatByUserId() {
		
		List<User> users = chatService.findDistinctUsersByRecentInteraction(authUser.getId());

		if (!users.isEmpty()) {
			return new ApiResponse<>("Your Recent chat menu are", users);
		}
		return new ApiResponse<>("No message found at this time", users);

	}

}
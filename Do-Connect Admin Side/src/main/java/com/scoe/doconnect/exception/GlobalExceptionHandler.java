package com.scoe.doconnect.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.scoe.doconnect.controller.ApiResponse; 
@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
	
		var errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

		logger.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(QuestionNotFoundException.class)
	public ResponseEntity<ErrorMessage> questionNotFoundException(QuestionNotFoundException exception) {

		var errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

		logger.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(AnswerNotFoundException.class)
	public ResponseEntity<ErrorMessage> answerNotFoundException(AnswerNotFoundException exception) {

		var errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

		logger.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorMessage> unauthorizedException(UnauthorizedException exception) {

		var errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());

		logger.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);

	}
	
	@ExceptionHandler(NotificationNotFoundException.class)
	public ResponseEntity<ErrorMessage> notificationNotFoundException(NotificationNotFoundException exception) {

		var errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		
		logger.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		
	}
	
	@ExceptionHandler(FieldValidationFailedException.class)
	public ResponseEntity<ApiResponse<List<String>>> fieldValidationFailedException(FieldValidationFailedException exception) {
		List<FieldError> errors = exception.getBindingResult().getFieldErrors();
		List<String> message = new ArrayList<>();

		for (FieldError e : errors) {
			message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
		}

		logger.error(exception.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Validation error in input field ", message));
		
	}

}

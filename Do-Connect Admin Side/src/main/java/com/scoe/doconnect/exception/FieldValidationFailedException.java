package com.scoe.doconnect.exception;

import org.springframework.validation.BindingResult;

public class FieldValidationFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BindingResult bindingResult;
	
	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public FieldValidationFailedException() {
		super();
	}
	public FieldValidationFailedException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
	
	 public FieldValidationFailedException(String message) {

	        super(message);

	    }

	    public FieldValidationFailedException(String message, Throwable cause) {

	        super(message, cause);

	    }

	    public FieldValidationFailedException(Throwable cause) {

	        super(cause);

	    }
}

package com.hcl.doconnect.exception;

@SuppressWarnings("serial")
public class AnswerNotFoundException extends Exception {
	
	public AnswerNotFoundException() {
        super();
    }

    public AnswerNotFoundException(String message) {

        super(message);

    }

    public AnswerNotFoundException(String message, Throwable cause) {

        super(message, cause);

    }

    public AnswerNotFoundException(Throwable cause) {

        super(cause);

    }
}

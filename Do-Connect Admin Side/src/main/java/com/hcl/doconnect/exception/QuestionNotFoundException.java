package com.hcl.doconnect.exception;

@SuppressWarnings("serial")
public class QuestionNotFoundException extends Exception {

	public QuestionNotFoundException() {

        super();

    }

    public QuestionNotFoundException(String message) {

        super(message);

    }

    public QuestionNotFoundException(String message, Throwable cause) {

        super(message, cause);

    }

    public QuestionNotFoundException(Throwable cause) {

        super(cause);

    }
}

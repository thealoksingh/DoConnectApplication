package com.hcl.doconnect.exception;



public class ChatNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatNotFoundException() {

	        super();

	    }

	    public ChatNotFoundException(String message) {

	        super(message);

	    }

	    public ChatNotFoundException(String message, Throwable cause) {

	        super(message, cause);

	    }

	    public ChatNotFoundException(Throwable cause) {

	        super(cause);

	    }
}

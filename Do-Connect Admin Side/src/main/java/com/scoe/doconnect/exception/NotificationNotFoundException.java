package com.scoe.doconnect.exception;

@SuppressWarnings("serial")
public class NotificationNotFoundException extends Exception {

	public NotificationNotFoundException() {
		super();
	}
	
	 public NotificationNotFoundException(String message) {

	        super(message);

	    }

	    public NotificationNotFoundException(String message, Throwable cause) {

	        super(message, cause);

	    }

	    public NotificationNotFoundException(Throwable cause) {

	        super(cause);

	    }

}

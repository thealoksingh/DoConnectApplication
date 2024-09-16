package com.scoe.doconnect.controller;

public class ApiResponse <T> {
    private String message;
    private T t;

    public ApiResponse(String message, T t) {
        this.message = message;
        this.t = t;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
    
}


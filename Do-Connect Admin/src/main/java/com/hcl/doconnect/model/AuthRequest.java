package com.hcl.doconnect.model;

public class AuthRequest {

	private String uname;
	private String password;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthRequest(String uname, String password) {
		super();
		this.uname = uname;
		this.password = password;
	}

	public AuthRequest() {
		super();
	}

	@Override
	public String toString() {
		return "AuthRequest [uname=" + uname + ", password=" + password + "]";
	}

}

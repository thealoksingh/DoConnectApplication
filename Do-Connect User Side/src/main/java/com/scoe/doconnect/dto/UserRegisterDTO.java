package com.scoe.doconnect.dto;

import javax.validation.constraints.Size;

public class UserRegisterDTO {
	@Size(min=2, max=30 ,message="Length should be greater than 2 and less than 30")
	private String username;
	@Size(min=8, message="Password must be length of atleast 8 character with combination of [Aa@1]")
    private String password;
    
	public UserRegisterDTO() {
		super();
	}

	public UserRegisterDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}

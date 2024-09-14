package com.hcl.doconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hcl.doconnect.controller.ApiResponse;
import com.hcl.doconnect.dto.SendChatDTO;
import com.hcl.doconnect.dto.UserLoginDTO;
import com.hcl.doconnect.dto.UserRegisterDTO;
import com.hcl.doconnect.model.User;

@Service
public class UserServiceImpl implements UserService {



	@Autowired
	private RestTemplate restTemplate;

	private String adminServiceUrl = "http://Do-Connect-Admin/";

	

	@Override
	public User registerUser(UserRegisterDTO userRegisterDTO) {

		User user = restTemplate.postForObject(adminServiceUrl + "api/admin/user/register", userRegisterDTO,
				User.class);

		return user;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApiResponse loginUser(UserLoginDTO userLoginDTO) {

		ApiResponse response = restTemplate.postForObject(adminServiceUrl + "api/admin/user/login", userLoginDTO,
				ApiResponse.class);

		return response;
	}

	
	

	@SuppressWarnings("rawtypes")
	@Override
	public ApiResponse sendChat(SendChatDTO sendChatDTO) {

		ApiResponse response = restTemplate.postForObject(adminServiceUrl + "api/admin/user/chat/send", sendChatDTO,
				ApiResponse.class);

		return response;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApiResponse viewChatByUserId(Long userId) {

		ApiResponse response = restTemplate.getForObject(adminServiceUrl + "api/admin/chat/user/" + userId,
				ApiResponse.class);

		return response;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApiResponse viewChatMenu(Long id) {

		ApiResponse response = restTemplate.getForObject(adminServiceUrl + "api/admin/user/recentChat/menu",
				ApiResponse.class);
		
		return response;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApiResponse findAllMessagesByUserId(Long id) {

		try {
			
		return restTemplate.getForObject(adminServiceUrl + "api/admin/user/chat/" + id,
				ApiResponse.class);
		} catch(HttpClientErrorException e) {
			return new ApiResponse<>("User not found with id = "+id, null);
		}

		
	}

	// Additional methods for user management
}

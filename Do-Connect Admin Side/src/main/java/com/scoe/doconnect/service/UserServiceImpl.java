package com.scoe.doconnect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoe.doconnect.model.User;
import com.scoe.doconnect.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
   

    @Override
    public User findById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()) {
        	return user.get();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User registerUser(User user) {
    	
    	user.setPassword(user.getPassword());
    	user.setRole("USER");
    	user.setQuestions(new ArrayList<>());
    	user.setAnswers(new ArrayList<>());
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void logoutUser(String username) {
       
    }

	@Override
	public User registerAdmin(User user) {
		if(loginUser(user.getUsername()) == null) {
			
		
		user.setPassword(user.getPassword());
		user.setRole("ADMIN");
    	user.setQuestions(new ArrayList<>());
    	user.setAnswers(new ArrayList<>());
        return userRepository.save(user);
		}
		
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findByRole("USER");
	}

	@Override
	public User updateUser(Long id, User user) {
		Optional<User> existingUser = userRepository.findById(id);
		if(existingUser.isPresent()) {
			
			if(!(user.getUsername().equals(""))) {
				existingUser.get().setUsername(user.getUsername());
			}if(!(user.getPassword().equals(""))) {
				existingUser.get().setPassword(user.getPassword());
			}if(!(user.getRole().equals(""))) {
				existingUser.get().setRole(user.getPassword());
			}
			
			return userRepository.save(existingUser.get());
		}
		return null;
	}

    // Additional methods for user management
}

package com.scoe.doconnect.service;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoe.doconnect.model.Chat;
import com.scoe.doconnect.model.User;
import com.scoe.doconnect.repository.ChatRepository;
import com.scoe.doconnect.repository.UserRepository;
 
@Service
public class ChatServiceImpl implements ChatService {
 
    @Autowired
	private  ChatRepository chatRepository;
    @Autowired
    private  UserRepository userRepository;
 
 
 
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }
 
    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }
 
    public List<Chat> findChatsBySenderId(Long senderId) {
        return chatRepository.findByFromId(senderId);
    }
 
    public List<Chat> findChatsByRecipientId(Long recipientId) {
        return chatRepository.findByToId(recipientId);
    }

	@Override
	public List<Chat> findChatsForUser(Long userId) {
		return chatRepository.findByFromIdOrToId(userId, userId);
	}
  
	
	@Override
	public List<User> findDistinctUsersByRecentInteraction(Long userId){
		List<Long> usersId = chatRepository.findDistinctUsersByRecentInteraction(userId);
		Set<User> users = new LinkedHashSet<>();
		for(Long l: usersId) {
			Optional<User> user = userRepository.findById(l);
			if(user.isPresent() && !users.contains(user.get())) {
			users.add(user.get());
			}
		}
		return new ArrayList<>(users);
	}
    // Additional methods as needed

	@Override
	public List<Chat> findMessagesBetweenUsers(Long userId, Long otherUserId) {
		
		Optional<User> user1 =  userRepository.findById(userId);
		Optional<User> user2 =   userRepository.findById(otherUserId);
		
		if(user1.isPresent() && user2.isPresent()) {
			
			return chatRepository.findByFromAndToOrFromAndTo(user1.get(), user2.get(), user2.get(), user1.get());
		}
		return null;
	}
}

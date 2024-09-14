package com.hcl.doconnect.service;

import java.util.List;
import java.util.Optional;

import com.hcl.doconnect.model.Chat;
import com.hcl.doconnect.model.User;

public interface ChatService {
	 Chat saveChat(Chat chat);
	 Optional<Chat> findById(Long id);
	 List<Chat> findChatsBySenderId(Long senderId);
	 List<Chat> findChatsByRecipientId(Long recipientId);
	List<Chat> findChatsForUser(Long userId);
	List<User> findDistinctUsersByRecentInteraction(Long userId);
	List<Chat> findMessagesBetweenUsers(Long userId, Long otherUserId);
}

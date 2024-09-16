package com.scoe.doconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoe.doconnect.exception.NotificationNotFoundException;
import com.scoe.doconnect.model.Notification;
import com.scoe.doconnect.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<Notification> getAllNotification() {
		
		return notificationRepository.findAll();
		
	}

	@Override
	public List<Notification> getAllUnreadNotifications() {
	
		return notificationRepository.findUnseenNotificationsSortedByDate();
	}

	@Override
	public Notification getNotificationsById(Long id) throws NotificationNotFoundException {
		
		return notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException("notification not found with given id = "+id));
	}

	@Override
	public void markedNotificationSeen(Notification notification) {
		
		notificationRepository.save(notification);
		
	}

}

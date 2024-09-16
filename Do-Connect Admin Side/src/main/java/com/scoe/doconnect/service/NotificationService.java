package com.scoe.doconnect.service;

import java.util.List;

import com.scoe.doconnect.exception.NotificationNotFoundException;
import com.scoe.doconnect.model.Notification;


public interface NotificationService {
	List<Notification> getAllNotification();

	List<Notification> getAllUnreadNotifications();

	Notification getNotificationsById(Long id) throws NotificationNotFoundException;

	void markedNotificationSeen(Notification notification);
}

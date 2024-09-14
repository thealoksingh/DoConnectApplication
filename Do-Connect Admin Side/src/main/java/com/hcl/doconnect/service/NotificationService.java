package com.hcl.doconnect.service;

import java.util.List;

import com.hcl.doconnect.exception.NotificationNotFoundException;
import com.hcl.doconnect.model.Notification;


public interface NotificationService {
	List<Notification> getAllNotification();

	List<Notification> getAllUnreadNotifications();

	Notification getNotificationsById(Long id) throws NotificationNotFoundException;

	void markedNotificationSeen(Notification notification);
}

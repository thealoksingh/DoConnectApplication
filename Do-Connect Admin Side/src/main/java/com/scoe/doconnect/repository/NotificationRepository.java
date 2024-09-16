package com.scoe.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scoe.doconnect.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	 @Query("SELECT n FROM Notification n WHERE n.status = 'UNSEEN' ORDER BY n.time ASC")
	    List<Notification> findUnseenNotificationsSortedByDate();
    // You can add custom query methods if needed
	
	
}

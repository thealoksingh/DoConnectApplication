package com.scoe.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scoe.doconnect.model.Chat;
import com.scoe.doconnect.model.User;
 
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByFromId(Long fromId);
    List<Chat> findByToId(Long toId);
	List<Chat> findByFromIdOrToId(Long userId, Long userId2);
	
	 @Query(value = "SELECT user_id FROM (" +
	           "SELECT FROM_ID AS user_id, MAX(TIME) AS recent_time FROM CHAT WHERE TO_ID = ?1 GROUP BY FROM_ID " +
	           "UNION " +
	           "SELECT TO_ID AS user_id, MAX(TIME) AS recent_time FROM CHAT WHERE FROM_ID = ?1 GROUP BY TO_ID) " +
	           "AS unified_results ORDER BY recent_time DESC", nativeQuery = true)
	    List<Long> findDistinctUsersByRecentInteraction(Long userId);
	 
	 
	 List<Chat> findByFromAndToOrFromAndTo(User from, User to, User to2, User from2);
	
}
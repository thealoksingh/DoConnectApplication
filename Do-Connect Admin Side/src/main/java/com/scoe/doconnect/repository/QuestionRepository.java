package com.scoe.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scoe.doconnect.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	
	List<Question> findByContentContainingIgnoreCase(String searchString);
	
	List<Question> findByStatus(String status);

	List<Question> findByContentContainingIgnoreCaseAndStatus(String searchString, String string);

	List<Question> findByTopicContainingIgnoreCaseAndStatus(String word, String string);

	List<Question> findByContentContainingIgnoreCaseAndTopicContainingIgnoreCase(String content, String topic);

}

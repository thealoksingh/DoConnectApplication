package com.scoe.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scoe.doconnect.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // You can add custom query methods if needed
	
	List<Answer> findByQuestionId(Long questionId);
	List<Answer> findByStatus(String status);
}

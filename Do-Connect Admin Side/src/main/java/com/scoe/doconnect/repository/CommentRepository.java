package com.scoe.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scoe.doconnect.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByAnswerId(Long questionId);

}

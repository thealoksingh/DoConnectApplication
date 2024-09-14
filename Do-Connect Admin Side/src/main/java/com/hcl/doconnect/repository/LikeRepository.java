package com.hcl.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.doconnect.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Like findByAnswerIdAndUserId(Long answerId, Long questionId);

}

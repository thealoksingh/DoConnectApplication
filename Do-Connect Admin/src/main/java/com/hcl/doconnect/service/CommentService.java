package com.hcl.doconnect.service;

import java.util.List;

import com.hcl.doconnect.dto.CommentDTO;
import com.hcl.doconnect.exception.AnswerNotFoundException;
import com.hcl.doconnect.model.Comment;
import com.hcl.doconnect.model.User;

public interface CommentService {
    Comment addComment(Long answerId, CommentDTO commentDTO, User user) throws AnswerNotFoundException;

	List<Comment> getCommentWithAnswerId(Long answerId) throws AnswerNotFoundException;
}
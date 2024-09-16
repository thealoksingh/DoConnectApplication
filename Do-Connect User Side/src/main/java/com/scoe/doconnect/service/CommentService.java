package com.scoe.doconnect.service;

import com.scoe.doconnect.dto.CommentDTO;
import com.scoe.doconnect.exception.AnswerNotFoundException;
import com.scoe.doconnect.model.Comment;
import com.scoe.doconnect.model.User;

public interface CommentService {
    Comment addComment(Long answerId, CommentDTO commentDTO, User user) throws AnswerNotFoundException;
}
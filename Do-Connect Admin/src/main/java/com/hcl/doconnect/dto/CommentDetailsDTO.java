package com.hcl.doconnect.dto;

import com.hcl.doconnect.model.Comment;

public class CommentDetailsDTO {

	private Comment comment;

	public CommentDetailsDTO(Comment comment) {
		this();
		this.comment = comment;
	}

	public CommentDetailsDTO() {
		super();
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}

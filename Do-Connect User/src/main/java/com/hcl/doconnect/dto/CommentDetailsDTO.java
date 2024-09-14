package com.hcl.doconnect.dto;

import com.hcl.doconnect.model.Comment;

public class CommentDetailsDTO {

	private Comment comment;

	public CommentDetailsDTO(Comment comment) {
		super();
		this.comment = comment;
	}

	public CommentDetailsDTO() {
		// TODO Auto-generated constructor stub
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}

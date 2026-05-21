package com.example.feed_app.exception;

public class CommentNotFoundException extends CustomException {
    
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}

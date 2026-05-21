package com.example.feed_app.exception;

public class ForbiddenException extends CustomException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}

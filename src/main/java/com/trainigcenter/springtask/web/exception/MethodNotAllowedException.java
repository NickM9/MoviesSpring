package com.trainigcenter.springtask.web.exception;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends WebException {

    private static final HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

    public MethodNotAllowedException() {
        super(status);
    }

    public MethodNotAllowedException(String message) {
        super(status, message);
    }

}

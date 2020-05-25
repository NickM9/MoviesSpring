package com.trainigcenter.springtask.web.exception;

public class MethodNotAllowedException extends RuntimeException {

    public MethodNotAllowedException(String message) {
        super(message);
    }
}

package com.trainigcenter.springtask.web.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ForbiddenException extends WebException {

    private static final HttpStatus status = HttpStatus.FORBIDDEN;

    public ForbiddenException() {
        super(status);
    }

    public ForbiddenException(String message) {
        super(status, message);
    }
}

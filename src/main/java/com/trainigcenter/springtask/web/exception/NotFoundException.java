package com.trainigcenter.springtask.web.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends WebException {

    private static final HttpStatus status = HttpStatus.NOT_FOUND;


    public NotFoundException() {
        super(status);
    }

    public NotFoundException(String message) {
        super(status, message);
    }

}

package com.trainigcenter.springtask.web.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class WebException extends RuntimeException {

    protected final HttpStatus status;

    public WebException(HttpStatus status) {
        this.status = status;
    }

    public WebException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }


}

package com.trainigcenter.springtask.web.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}

package com.trainigcenter.springtask.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForbiddenException extends RuntimeException {

    private String name;

}

package com.trainigcenter.springtask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForbiddenException extends RuntimeException {

    private String name;

}

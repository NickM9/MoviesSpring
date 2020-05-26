package com.trainigcenter.springtask.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Error {

    private int code;
    private String message;
    private List<String> details;


    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(int code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

}

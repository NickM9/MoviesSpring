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
    private List<FieldError> fieldErrors = new ArrayList<>();

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addFieldError(String objectName, String field, String message) {
        FieldError error = new FieldError(objectName, field, message);
        fieldErrors.add(error);
    }
}

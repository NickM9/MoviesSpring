package com.trainigcenter.springtask.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private int code;
    private String message;
    private List<ErrorField> details;


    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

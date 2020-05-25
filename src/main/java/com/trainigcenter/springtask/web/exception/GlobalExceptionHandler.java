package com.trainigcenter.springtask.web.exception;

import com.trainigcenter.springtask.web.dto.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "validation error");
        for (FieldError fieldError : fieldErrors){
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getStackTrace().toString());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Method not supported");
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedException(MethodNotAllowedException e, WebRequest request) {
        Error error = new Error(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}

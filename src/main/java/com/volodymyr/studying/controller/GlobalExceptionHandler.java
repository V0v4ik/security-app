package com.volodymyr.studying.controller;

import com.volodymyr.studying.exceptions.NoSuchUserException;
import com.volodymyr.studying.exceptions.UserAlreadyExistException;
import com.volodymyr.studying.validation.ValidationErrorResponse;
import com.volodymyr.studying.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(NoSuchUserException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String userAlreadyExistHandler(UserAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String JsonParseExceptionHandler(HttpMessageNotReadableException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String unexpectedExceptionHandler(Exception ex) {
        return "Sorry about unexpected issue. Error log:\n" + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse constraintValidationExceptionHandler(
            ConstraintViolationException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            response.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return response;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            response.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return response;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse methodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(
                new Violation(ex.getParameter().getParameterName(), ex.getMessage()));
        return response;
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse missingServletRequestParameterExceptionHandler(
            MissingServletRequestParameterException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(
                new Violation(ex.getParameterName(), ex.getMessage()));
        return response;
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse bindExceptionHandler(
            BindException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            response.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return response;
    }
}

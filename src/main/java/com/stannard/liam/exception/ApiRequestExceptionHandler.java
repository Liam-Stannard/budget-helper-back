package com.stannard.liam.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiRequestExceptionHandler {

  @ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
    ApiRequestException apiRequestException = new ApiRequestException(e.getMessage(),
        e.getHttpStatus(), e.getTimestamp());

    return new ResponseEntity<>(apiRequestException, apiRequestException.getHttpStatus());
  }

}

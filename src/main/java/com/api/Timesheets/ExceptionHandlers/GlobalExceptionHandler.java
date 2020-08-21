package com.api.Timesheets.ExceptionHandlers;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(1)
public class GlobalExceptionHandler {

  @ExceptionHandler(value = GlobalException.class)
  public ResponseEntity<String> handleGlobalException(
      HttpServletRequest request, GlobalException exception) {
    return new ResponseEntity<>(exception.getJSONMessage(), exception.getHttpCode());
  }
}

package com.employee.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResouceNotFoundException extends Exception {
  
  public ResouceNotFoundException(String message){
    super(message);
  }
}

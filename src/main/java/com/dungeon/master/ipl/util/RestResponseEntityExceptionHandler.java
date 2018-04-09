package com.dungeon.master.ipl.util;

import javax.ws.rs.core.Response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = { PointsException.class })
    protected ResponseEntity<ErrorDetails> handleForbidden(RuntimeException ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), Status.FORBIDDEN);
        
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

}

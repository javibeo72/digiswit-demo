package com.digiswit.exercise1.confederation.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationError(ConstraintViolationException exception) {
    	
        LOGGER.warn("ConstraintViolationException thrown", exception);
        
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
        	
            Map<String, String> transformedError = new HashMap<>();
            
            String fieldName = violation.getPropertyPath().toString();
            transformedError.put("field", fieldName.substring(fieldName.lastIndexOf('.') + 1));
            transformedError.put("error", violation.getMessage());

            errors.add(transformedError);
        }
        
        response.put("errors", errors);

        return response;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT) 
    public Map<String, Object> handleValidationError(DataIntegrityViolationException exception) {
    	
        LOGGER.warn("DataIntegrityViolationException thrown", exception);
        
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        Map<String, String> transformedError = new HashMap<>();
        
        
        // TO-DO Handle exception to retrieve field name and error message
        transformedError.put("field", "username");
        transformedError.put("error", "Value already used");

        errors.add(transformedError);        
        
        response.put("errors",errors );

        return response;
    }

   
    
    
    
  /*  @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationError(MethodArgumentNotValidException exception) {
        LOGGER.warn("MethodArgumentNotValidException thrown", exception);
        Map<String, Object> response = new HashMap<>();

        if (exception.hasFieldErrors()) {
            List<Map<String, String>> errors = new ArrayList<>();

            for (FieldError error : exception.getFieldErrors()) {
                Map<String, String> transformedError = new HashMap<>();
                transformedError.put("field", error.getField());
                transformedError.put("error", error.getDefaultMessage());

                errors.add(transformedError);
            }
            response.put("errors", errors);
        }

        return response;
    } */
}

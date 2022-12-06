package com.digiswit.exercise1.confederation.app.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<IDateValidator, String>
{

	public boolean isValid(String date, ConstraintValidatorContext cxt) {
       	
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        
        return true;
        
    }
	
}



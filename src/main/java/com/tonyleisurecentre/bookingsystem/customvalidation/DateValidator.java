package com.tonyleisurecentre.bookingsystem.customvalidation;

import com.tonyleisurecentre.bookingsystem.Util.DateTimeUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<DateConstraint, String> {

    @Override
    public void initialize(DateConstraint date) {
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("DateValidator::isValid:dateStr: ".concat(str));
        try {
            DateTimeUtil.strToLocalDate(str);
        } catch (DateTimeParseException ex) {
            System.out.println("DateValidator: The Date String is not valid");
            return false;
        }
        return true;
    }
}

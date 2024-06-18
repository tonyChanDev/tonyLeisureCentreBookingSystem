package com.tonyleisurecentre.bookingsystem.customvalidation;

import com.tonyleisurecentre.bookingsystem.enums.FacilityEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class FacilityTypeValidator implements ConstraintValidator<FacilityTypeConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            FacilityEnum.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }
}

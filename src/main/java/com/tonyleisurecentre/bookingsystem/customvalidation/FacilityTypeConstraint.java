package com.tonyleisurecentre.bookingsystem.customvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FacilityTypeValidator.class)
@Target( {ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FacilityTypeConstraint {
    String message() default "Invalid Facility Type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.tonyleisurecentre.bookingsystem.customvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target( {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "Invalid date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

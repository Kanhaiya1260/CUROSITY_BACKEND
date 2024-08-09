package com.app.validations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({TYPE})
@Constraint(validatedBy = StartDateBeforeEndValidator.class)
public @interface StartDateBeforeEnd {
	String message() default "Start date must be before end date";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

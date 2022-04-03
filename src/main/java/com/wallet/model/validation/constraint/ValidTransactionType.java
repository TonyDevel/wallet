package com.wallet.model.validation.constraint;

import com.wallet.model.validation.validator.ValidTransactionTypeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTransactionTypeValidator.class)
public @interface ValidTransactionType {
    String message() default "Invalid transaction type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.wallet.model.validation.constraint;

import com.wallet.model.validation.validator.SufficientFundsValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SufficientFundsValidator.class)
public @interface SufficientFunds {
    String message() default "Couldn't create transaction due to insufficient funds";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

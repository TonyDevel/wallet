package com.wallet.model.validation.constraint;

import com.wallet.model.validation.validator.AccountExistsValidator;

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
@Constraint(validatedBy = AccountExistsValidator.class)
public @interface AccountExists {
    String message() default "Account doesn't exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

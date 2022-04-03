package com.wallet.model.validation.validator;

import com.wallet.model.entity.TransactionType;
import com.wallet.model.validation.constraint.ValidTransactionType;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidTransactionTypeValidator implements ConstraintValidator<ValidTransactionType, String> {

    @Override
    public boolean isValid(String transactionType, ConstraintValidatorContext context) {
        if (transactionType == null) {
            return true;
        }
        try {
            TransactionType.valueOfIgnoreCase(transactionType);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}

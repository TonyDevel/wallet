package com.wallet.model.validation.validator;

import com.wallet.model.validation.constraint.TransactionDoesNotExists;
import com.wallet.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class TransactionDoesNotExistsValidator implements ConstraintValidator<TransactionDoesNotExists, Integer> {

    private final ITransactionService transactionService;

    @Override
    public boolean isValid(Integer transactionId, ConstraintValidatorContext context) {
        if (transactionId == null) {
            return true;
        }

        return !transactionService.exists(transactionId);
    }
}

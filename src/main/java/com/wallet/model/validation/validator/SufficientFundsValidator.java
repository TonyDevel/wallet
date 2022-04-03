package com.wallet.model.validation.validator;

import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.model.entity.TransactionType;
import com.wallet.model.validation.constraint.SufficientFunds;
import com.wallet.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class SufficientFundsValidator implements ConstraintValidator<SufficientFunds, TransactionRequest> {

    private final IAccountService accountService;

    @Override
    public boolean isValid(TransactionRequest transactionRequest, ConstraintValidatorContext context) {
        TransactionType transactionType = TransactionType.valueOfIgnoreCase(transactionRequest.getTransactionType());
        if (TransactionType.DEBIT.equals(transactionType)) {
            return accountService.sufficientFundsToWithdraw(
                    transactionRequest.getAccountId(), transactionRequest.getAmount()
            );
        }
        return true;
    }
}

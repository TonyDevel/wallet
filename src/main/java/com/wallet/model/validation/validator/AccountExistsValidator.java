package com.wallet.model.validation.validator;

import com.wallet.model.validation.constraint.AccountExists;
import com.wallet.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class AccountExistsValidator implements ConstraintValidator<AccountExists, Integer> {

    private final IAccountService accountService;

    @Override
    public boolean isValid(Integer accountId, ConstraintValidatorContext context) {
        if (accountId == null) {
            return true;
        }
        return accountService.exists(accountId);
    }
}

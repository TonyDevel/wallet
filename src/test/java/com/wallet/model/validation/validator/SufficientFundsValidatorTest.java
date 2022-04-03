package com.wallet.model.validation.validator;

import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.model.entity.TransactionType;
import com.wallet.service.IAccountService;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SufficientFundsValidatorTest {

    private final Integer accountId = 14;

    @InjectMocks
    private SufficientFundsValidator validator;

    @Mock
    private IAccountService accountService;

    @Test
    public void testCredit() {
        TransactionRequest transactionRequest = buildRequest(TransactionType.CREDIT);

        boolean valid = validator.isValid(transactionRequest, null);
        assertTrue(valid);
        verify(accountService, times(0)).sufficientFundsToWithdraw(anyInt(), any());
    }

    @Test
    public void testDebitPositive() {
        TransactionRequest transactionRequest = buildRequest(TransactionType.DEBIT);
        when(accountService.sufficientFundsToWithdraw(eq(accountId), any())).thenReturn(true);

        boolean valid = validator.isValid(transactionRequest, null);
        assertTrue(valid);
        verify(accountService, times(1)).sufficientFundsToWithdraw(eq(accountId), any());
    }

    @Test
    public void testDebitNegative() {
        TransactionRequest transactionRequest = buildRequest(TransactionType.DEBIT);
        when(accountService.sufficientFundsToWithdraw(eq(accountId), any())).thenReturn(false);

        boolean valid = validator.isValid(transactionRequest, null);
        assertFalse(valid);
        verify(accountService, times(1)).sufficientFundsToWithdraw(eq(accountId), any());
    }

    private TransactionRequest buildRequest(TransactionType transactionType) {
        TransactionRequest request = new TransactionRequest();
        request.setTransactionType(transactionType.name());
        request.setAccountId(accountId);
        request.setAmount(BigDecimal.TEN);
        return request;
    }
}

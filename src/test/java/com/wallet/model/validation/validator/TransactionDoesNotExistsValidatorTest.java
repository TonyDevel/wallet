package com.wallet.model.validation.validator;

import com.wallet.service.ITransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDoesNotExistsValidatorTest {

    private final Integer transactionId = 1244;

    @InjectMocks
    private TransactionDoesNotExistsValidator validator;

    @Mock
    private ITransactionService transactionService;

    @Test
    public void testValidPositive() {
        when(transactionService.exists(transactionId)).thenReturn(false);

        boolean valid = validator.isValid(transactionId, null);
        assertTrue(valid);
        verify(transactionService, times(1)).exists(transactionId);
    }

    @Test
    public void testValidNegative() {
        when(transactionService.exists(transactionId)).thenReturn(true);

        boolean valid = validator.isValid(transactionId, null);
        assertFalse(valid);
        verify(transactionService, times(1)).exists(transactionId);
    }
}
